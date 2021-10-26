package com.karrotmvp.ourapt.v1.preopen;


import com.karrotmvp.ourapt.v1.auth.CurrentUser;
import com.karrotmvp.ourapt.v1.auth.springsecurity.KarrotOpenApiUserProfileDto;
import com.karrotmvp.ourapt.v1.common.dto.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.exception.application.DuplicatedRequestException;
import com.karrotmvp.ourapt.v1.preopen.dto.PreopenReservationDto;
import com.karrotmvp.ourapt.v1.preopen.entity.PreopenVotingForm;
import com.karrotmvp.ourapt.v1.user.User;
import com.karrotmvp.ourapt.v1.user.UserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;


@RestController
@RequestMapping(value = "/api/v1/preopen")
public class PreopenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreopenRepository preopenRepository;

    @PostMapping(value = "/reservation")
    @ApiOperation(value = "사전예약 알림 신청")
    @Transactional
    public CommonResponseBody<Void> reservationNotification(
            @RequestBody @Valid PreopenReservationDto submit,
            @CurrentUser KarrotOpenApiUserProfileDto userProfile
    ) {
        User foundUser = userRepository
                .findById(userProfile.getUserId())
                .orElseThrow(() -> new RuntimeException("등록되지 않은 유저입니다."));

        foundUser.setPushAgreedAt(new Date());
        userRepository.save(foundUser);

        preopenRepository.findById(foundUser.getKarrotId()).ifPresent((p) -> {
            throw new DuplicatedRequestException("이미 신청된 유저입니다.", "이미 알림신청하셨네요! 서비스가 오픈하면 알려드릴게요!");
        });

        PreopenVotingForm newReservation = new PreopenVotingForm();
        newReservation.setUser(foundUser);
        newReservation.setRegionId(submit.getRegionId());
        newReservation.setResult(true, true, true);

        preopenRepository.save(newReservation);

        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}