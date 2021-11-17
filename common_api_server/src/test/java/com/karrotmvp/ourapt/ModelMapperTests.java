package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
import com.karrotmvp.ourapt.v1.comment.Comment;
import com.karrotmvp.ourapt.v1.comment.dto.model.CommentDto;
import com.karrotmvp.ourapt.v1.article.question.Question;
import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.user.dto.model.UserDto;
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
    Apartment sourceApt = new Apartment();
    sourceApt.setName("test");
    sourceApt.setRegionDepth1(getTestRegion());
    sourceApt.setRegionDepth2(getTestRegion());
    sourceApt.setRegionDepth3(getTestRegion());
    sourceApt.setRegionDepth4(getTestRegion());
    sourceApt.setInactiveAt(getYesterday());

    KarrotProfile sourceProfile = new KarrotProfile(
      sourceUserId, "nickname", "image");
    User sourceUser = new User(sourceUserId, sourceProfile);
    Date now = new Date();
    sourceUser.setCreatedAt(now);
    sourceUser.setUpdatedAt(now);
    sourceUser.setPushAgreedAt(getYesterday());
    sourceUser.setBannedAt(now);
    sourceUser.setAdmin(true);
    sourceUser.setCheckedIn(sourceApt);
    UserDto result = modelMapper.map(sourceUser, UserDto.class);

    assertEquals(sourceUser.isAdmin(), result.isAdmin());
    assertEquals(sourceUserId, result.getId());
    assertEquals(sourceProfile.getId(), result.getProfile().getId());
    assertEquals(sourceProfile.getNickname(), result.getProfile().getNickname());
    assertEquals(sourceProfile.getProfileImageUrl(), result.getProfile().getProfileImageUrl());
    assertTrue(result.getIsPushAgreed());
    assertNotNull(result.getCreatedAt());
    assertNotNull(result.getUpdatedAt());
    assertNotNull(result.getBannedAt());
    assertEquals(sourceApt.getId(), result.getCheckedIn().getId());
    assertEquals(sourceApt.getName(), result.getCheckedIn().getName());
    assertEquals(sourceApt.getRegionDepth1().getId(), result.getCheckedIn().getRegionDepth1().getId());
    assertEquals(sourceApt.getRegionDepth1().getName(), result.getCheckedIn().getRegionDepth1().getName());
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
    assertFalse(result.isActive());
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
  void questionMappingTest() {
    Date now = new Date();
    Question source = new Question();
    User testUser = new User("id", new KarrotProfile("id", "nick", "img"), true);
    Region testRegion = getTestRegion();
    source.setMainText("mainText");
    source.setWriter(testUser);
    source.setCreatedAt(now);
    source.setRegionWhereCreated(testRegion.getId());
    source.setUpdatedAt(now);

    QuestionDto result = modelMapper.map(source, QuestionDto.class);
    assertEquals(source.getId(), result.getId());
    assertEquals(source.isByAdmin(), result.getByAdmin());
    assertEquals(source.getWriter().getProfile().getId(), result.getWriter().getId());
    assertEquals(source.getWriter().getProfile().getNickname(), result.getWriter().getNickname());
    assertEquals(source.getWriter().getProfile().getProfileImageUrl(), result.getWriter().getProfileImageUrl());
    assertEquals(source.getCountOfComments(), result.getCountOfComments());
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
    source.setRegionWhereCreated(testRegion.getId());
    source.setUpdatedAt(now);

    CommentDto result = modelMapper.map(source, CommentDto.class);
    assertEquals(source.getId(), result.getId());
    assertEquals(source.getWriter().getProfile().getId(), result.getWriter().getId());
    assertEquals(source.getWriter().getProfile().getNickname(), result.getWriter().getNickname());
    assertEquals(source.getWriter().getProfile().getProfileImageUrl(), result.getWriter().getProfileImageUrl());
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

  private Date getTomorrow() {
    Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, 1);
    return c.getTime();
  }

  private Region getTestRegion() {
    return Static.regionDict.get("test");
  }
}
