package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionAnswerDto;
import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestion;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestionAnswer;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.article.comment.Comment;
import com.karrotmvp.ourapt.v1.article.comment.dto.CommentDto;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.user.dto.UserDto;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import com.karrotmvp.ourapt.v1.user.entity.User;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ModelMapperTests {

  @Autowired
  private ModelMapper modelMapper;

  @Test
  void regionMappingTest() {
    Region sourceRegion = getTestRegion();
    RegionDto result = modelMapper.map(sourceRegion, RegionDto.class);
    assertNotNull(result.getId());
    assertNotNull(result.getName());
  }

  @Test
  void userMappingTest() {
    String sourceUserId = "id";
    KarrotProfile sourceProfile = new KarrotProfile(
      sourceUserId, "nickname", "image");
    User sourceUser = new User(sourceUserId, sourceProfile);
    Date now = new Date();
    sourceUser.setCreatedAt(now);
    sourceUser.setUpdatedAt(now);
    sourceUser.setPushAgreedAt(getYesterday());
    sourceUser.setBannedAt(now);
    UserDto result = modelMapper.map(sourceUser, UserDto.class);
    assertEquals(sourceUserId, result.getId());
    assertEquals(sourceProfile.getId(), result.getProfile().getId());
    assertEquals(sourceProfile.getNickname(), result.getProfile().getNickname());
    assertEquals(sourceProfile.getProfileImageUrl(), result.getProfile().getProfileImageUrl());
    assertTrue(result.getIsPushAgreed());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
    assertNotNull(result.getBannedAt());
  }

  @Test
  void apartmentMappingTest() {
    Apartment sourceApt = new Apartment();
    Date now = new Date();
    sourceApt.setName("regionName");
    sourceApt.setInactiveAt(getYesterday());
    sourceApt.setCreatedAt(now);
    sourceApt.setUpdatedAt(now);
    sourceApt.setRegionDepth1(getTestRegion());
    sourceApt.setRegionDepth2(getTestRegion());
    sourceApt.setRegionDepth3(getTestRegion());
    sourceApt.setRegionDepth4(getTestRegion());
    ApartmentDto result = modelMapper.map(sourceApt, ApartmentDto.class);
    assertEquals(sourceApt.getName(), result.getName());
    assertFalse(result.getIsActive());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
    assertEquals(result.getRegionDepth1().getId(), sourceApt.getRegionDepth1().getId());
    assertEquals(result.getRegionDepth1().getName(), sourceApt.getRegionDepth1().getName());
    assertEquals(result.getRegionDepth2().getId(), sourceApt.getRegionDepth2().getId());
    assertEquals(result.getRegionDepth2().getName(), sourceApt.getRegionDepth2().getName());
    assertEquals(result.getRegionDepth3().getId(), sourceApt.getRegionDepth3().getId());
    assertEquals(result.getRegionDepth3().getName(), sourceApt.getRegionDepth3().getName());
    assertEquals(result.getRegionDepth4().getId(), sourceApt.getRegionDepth4().getId());
    assertEquals(result.getRegionDepth4().getName(), sourceApt.getRegionDepth4().getName());

  }

  @Test
  void adminQuestionMappingTest() {
    Date now = new Date();
    Region testRegion = getTestRegion();
    AdminQuestion adminQuestion = new AdminQuestion();
    adminQuestion.setDisplayOn(testRegion.getId());
    adminQuestion.setInactiveAt(getYesterday());
    adminQuestion.setMainText("mainText");
    adminQuestion.setExpiredAt(now);
    adminQuestion.setCreatedAt(now);
    adminQuestion.setUpdatedAt(now);
    AdminQuestionDto result = modelMapper.map(adminQuestion, AdminQuestionDto.class);

    assertEquals(testRegion.getId(), result.getDisplayOn().getId());
    assertEquals(testRegion.getName(), result.getDisplayOn().getName());
    assertFalse(result.getIsActive());
    assertNotNull(result.getMainText());
    assertNotNull(result.getExpiredAt());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
  }

  @Test
  void adminQuestionAnswerMappingTest() {
    Date now = new Date();
    AdminQuestion adminQuestion = new AdminQuestion();
    AdminQuestionAnswer source = new AdminQuestionAnswer();
    User answerer = new User("id", new KarrotProfile("id", "nickname", "img"));
    Region madeIn = getTestRegion();
    source.setMainText("mainText");
    source.setAbout(adminQuestion);
    source.setAnswerer(answerer);
    source.setCreatedOn(madeIn.getId());
    source.setCreatedAt(now);
    source.setUpdatedAt(now);
    AdminQuestionAnswerDto result = modelMapper.map(source, AdminQuestionAnswerDto.class);
    assertNotNull(result.getId());
    assertNotNull(result.getAnswerer());
    assertNotNull(result.getMainText());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
  }

  @Test
  void questionMappingTest() {
    Date now = new Date();
    Question source = new Question();
    User testUser = new User("id", new KarrotProfile("id", "nick", "img"));
    Region testRegion = getTestRegion();
    source.setMainText("mainText");
    source.setWriter(testUser);
    source.setCreatedAt(now);
    source.setCreatedOn(testRegion.getId());
    source.setUpdatedAt(now);

    QuestionDto result = modelMapper.map(source, QuestionDto.class);
    assertEquals(source.getId(), result.getId());
    assertEquals(source.getWriter().getId(), result.getWriter().getId());
    assertEquals(source.getWriter().getNickname(), result.getWriter().getNickname());
    assertEquals(source.getWriter().getProfileImageUrl(), result.getWriter().getProfileImageUrl());
    assertNotNull(result.getMainText());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
  }

  void commentMappingTest() {
    Date now = new Date();
    Comment source = new Comment();
    User testUser = new User("id", new KarrotProfile("id", "nick", "img"));
    Region testRegion = getTestRegion();
    source.setMainText("mainText");
    source.setWriter(testUser);
    source.setCreatedAt(now);
    source.setCreatedOn(testRegion.getId());
    source.setUpdatedAt(now);

    CommentDto result = modelMapper.map(source, CommentDto.class);
    assertEquals(source.getId(), result.getId());
    assertEquals(source.getWriter().getId(), result.getWriter().getId());
    assertEquals(source.getWriter().getNickname(), result.getWriter().getNickname());
    assertEquals(source.getWriter().getProfileImageUrl(), result.getWriter().getProfileImageUrl());
    assertNotNull(result.getMainText());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
  }


  private Date getYesterday() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -1);
    return c.getTime();
  }

  private Region getTestRegion() {
    return Static.regionDict.get("test");
  }
}
