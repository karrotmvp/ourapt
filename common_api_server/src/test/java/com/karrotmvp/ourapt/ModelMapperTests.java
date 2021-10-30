package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionAnswerDto;
import com.karrotmvp.ourapt.v1.adminquestion.dto.model.AdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestion;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestionAnswer;
import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.apartment.dto.model.RegionDto;
import com.karrotmvp.ourapt.v1.apartment.entity.Apartment;
import com.karrotmvp.ourapt.v1.apartment.entity.Region;
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
    void RegionMappingTest(){
        Region sourceRegion = makeTestRegion(4);
        RegionDto result = modelMapper.map(sourceRegion, RegionDto.class);
        assertNotNull(result.getId());
        assertNotNull(result.getName());
    }

    @Test
    void UserMappingTest() {
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
    void ApartmentMappingTest() {
        Apartment sourceApt = new Apartment();
        Date now = new Date();
        sourceApt.setName("regionName");
        sourceApt.setInactiveAt(getYesterday());
        sourceApt.setCreatedAt(now);
        sourceApt.setUpdatedAt(now);
        sourceApt.setRegionDepth1(makeTestRegion(1));
        sourceApt.setRegionDepth2(makeTestRegion(2));
        sourceApt.setRegionDepth3(makeTestRegion(3));
        sourceApt.setRegionDepth4(makeTestRegion(4));
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
        Region testRegion = makeTestRegion(3);
        AdminQuestion adminQuestion = new AdminQuestion();
        adminQuestion.setDisplayOn(testRegion);
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
    void AdminQuestionAnswerMappingTest() {
        Date now = new Date();
        AdminQuestion adminQuestion = new AdminQuestion();
        AdminQuestionAnswer source = new AdminQuestionAnswer();
        User answerer = new User("id", new KarrotProfile("id", "nickname", "img"));
        Region madeIn = makeTestRegion(4);
        source.setMainText("mainText");
        source.setAbout(adminQuestion);
        source.setAnswerer(answerer);
        source.setCreatedOn(madeIn);
        source.setCreatedAt(now);
        source.setUpdatedAt(now);
        AdminQuestionAnswerDto result = modelMapper.map(source, AdminQuestionAnswerDto.class);
        assertNotNull(result.getId());
        assertNotNull(result.getAnswerer());
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
    private Region makeTestRegion(int depth) {
        return new Region("regionId" + depth, "regionName" + depth);
    }
}
