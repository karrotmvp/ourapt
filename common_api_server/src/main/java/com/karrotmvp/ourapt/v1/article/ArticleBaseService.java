package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.common.exception.application.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.common.exception.application.NoPermissionException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ArticleBaseService<T extends Article, D> {

  protected final ArticleRepository<T> articleRepository;
  protected final ArticleCustomRepository<T, String> articleCustomRepository;
  protected final ModelMapper mapper;

  public ArticleBaseService(ArticleRepository<T> articleRepository, ArticleCustomRepository<T, String> articleCustomRepository, ModelMapper mapper) {
    this.articleRepository = articleRepository;
    this.articleCustomRepository = articleCustomRepository;
    this.mapper = mapper;
  }

  public D getOneById(String articleId) {
    return mapper.map(this.safelyGetById(articleId), this.getClassOfDomainModel());
  }

  // not used
  public List<D> getPageOfApartmentWithDateCursor(String apartmentId, Date cursor, int perPage) {
    return this.articleCustomRepository
      .findFirstByApartmentIdToAndDateCursorByOrderByDesc(apartmentId, cursor, PageRequest.of(0, perPage))
      .stream()
      .map(q -> mapper.map(q, getClassOfDomainModel()))
      .collect(Collectors.toList());
  }

  protected <U> List<U> getPageAndOriginWithOffsetCursor(int perPage, int pageNum, Class<U> outputClass) {
    return this.articleCustomRepository.findByOffsetCursor(PageRequest.of(pageNum, perPage))
      .stream().map(q -> mapper.map(q, outputClass)).collect(Collectors.toList());
  }

  public int getCountOfAll() {
    return Math.toIntExact(this.articleRepository.countByDeletedAtIsNull());
  }

  public int getCountInToday() {
    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();
    Date now = new Date();
    from.setTime(now);
    from.set(Calendar.HOUR, 0);
    from.set(Calendar.MINUTE, 0);
    from.set(Calendar.SECOND, 0);
    to.setTime(now);
    to.add(Calendar.DATE, 1);
    to.set(Calendar.HOUR, 0);
    to.set(Calendar.MINUTE, 0);
    to.set(Calendar.SECOND, 0);
    return Math.toIntExact(this.articleRepository.countByCreatedAtBetween(from.getTime(), to.getTime()));
  }

  @Transactional
  public D updateMainTextById(String articleId, String updaterId, String mainText) {
    T toUpdate = this.safelyGetById(articleId);
    if (!toUpdate.getWriter().getId().equals(updaterId)) {
      throw new NoPermissionException("You has no permission to update this");
    }
    toUpdate.setMainText(mainText);
    this.articleRepository.save(toUpdate);
    return mapper.map(toUpdate, this.getClassOfDomainModel());
  }

  @Transactional
  public D deleteById(String articleId) {
    T toDelete = this.safelyGetById(articleId);
    toDelete.setDeletedAt(new Date());
    this.articleRepository.save(toDelete);
    return mapper.map(toDelete, getClassOfDomainModel());
  }

  public T safelyGetById(String articleId) {
    return this.articleRepository.findById(articleId).orElseThrow(
      () -> new DataNotFoundFromDBException("There is no article match with ID: " + articleId)
    );
  }

  protected abstract Class<D> getClassOfDomainModel();
}
