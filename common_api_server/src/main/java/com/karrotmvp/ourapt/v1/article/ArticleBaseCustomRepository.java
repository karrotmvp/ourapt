package com.karrotmvp.ourapt.v1.article;

import com.karrotmvp.ourapt.v1.common.BaseEntityCreatedDateComparator;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.common.Utils;
import com.karrotmvp.ourapt.v1.common.karrotoapi.KarrotOAPI;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ArticleBaseCustomRepository<T extends Article> {

  protected final EntityManager em;
  protected final KarrotOAPI karrotOAPI;

  protected ArticleBaseCustomRepository(EntityManager em, KarrotOAPI karrotOAPI) {
    this.em = em;
    this.karrotOAPI = karrotOAPI;
  }

  protected List<T> joinOnKarrotProfile(TypedQuery<T> query) {
    Set<String> writerIds = new HashSet<>();
    List<T> incompleteArticles = query.getResultList()
      .stream()
      .peek(article -> writerIds.add(article.getWriter().getId()))
      .peek(article -> article.getWriter().setProfile(article.isByAdmin() ? Static.makeAdminKarrotProfile(article.getId()) : null))
      .collect(Collectors.toList());
    List<KarrotProfile> profiles = this.karrotOAPI.getKarrotUserProfilesByIds(writerIds);

    return Utils.leftOuterHashJoin(
      incompleteArticles,
      profiles,
      (article) -> article.getWriter().getId(),
      KarrotProfile::getId,
      (article, profile) -> article.getWriter().setProfile(profile))
      .stream()
      .sorted(new BaseEntityCreatedDateComparator(BaseEntityCreatedDateComparator.Order.DESC))
      .collect(Collectors.toList());
  }
}