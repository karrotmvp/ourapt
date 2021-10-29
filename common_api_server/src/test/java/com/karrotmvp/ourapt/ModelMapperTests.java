package com.karrotmvp.ourapt;

import com.karrotmvp.ourapt.v1.adminquestion.dto.AdminQuestionDto;
import com.karrotmvp.ourapt.v1.adminquestion.entity.AdminQuestion;
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
    void adminQuestionMappingTest() {
        Date now = new Date();
        Region testRegion = new Region("regionId", "regionName");
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

    private Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }
}
