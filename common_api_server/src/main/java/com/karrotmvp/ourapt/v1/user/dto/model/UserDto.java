package com.karrotmvp.ourapt.v1.user.dto.model;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;
import com.karrotmvp.ourapt.v1.user.entity.KarrotProfile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private String id;
    private KarrotProfile profile;
    private Boolean isPushAgreed;
    private Date bannedAt;
    private Date createdAt;
    private Date updatedAt;
    private ApartmentDto checkedIn;
    private boolean isAdmin;
}