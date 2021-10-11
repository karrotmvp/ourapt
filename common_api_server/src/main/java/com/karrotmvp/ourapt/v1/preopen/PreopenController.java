package com.karrotmvp.ourapt.v1.preopen;

import com.karrotmvp.ourapt.v1.auth.KarrotAuthenticationToken;
import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import com.karrotmvp.ourapt.v1.common.constant.ApiResult;
import com.karrotmvp.ourapt.v1.exception.DataNotFoundFromDBException;
import com.karrotmvp.ourapt.v1.exception.DuplicatedRequestException;
import com.karrotmvp.ourapt.v1.preopen.dto.PreopenVotingCountDto;
import com.karrotmvp.ourapt.v1.preopen.dto.PreopenVotingFormDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/preopen")
public class PreopenController {

    // @Autowired
    // private UserService userService;

    @Autowired
    private PreopenRepository preopenRepository;

    @GetMapping("/me/voting")
    public CommonResponseBody<PreopenVotingFormDto> getMyVotedForm(KarrotAuthenticationToken authentication) {
        String userId = String.valueOf(authentication.getPrincipal().getUserId());
        PreopenVotingForm foundVotingForm = preopenRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundFromDBException("Cannot find voting data matched with user", ""));

        return CommonResponseBody.<PreopenVotingFormDto>builder()
                .success()
                .data(PreopenVotingFormDto.fromEntity(foundVotingForm))
                .build();
    }

    @GetMapping("/voting/count")
    public CommonResponseBody<PreopenVotingCountDto> getCurrentVotingCount() {
        List<PreopenVotingForm> votings =  preopenRepository.findAll();
        return CommonResponseBody.<PreopenVotingCountDto>builder()
                .success()
                .data(new PreopenVotingCountDto(votings.size()))
                .build();
    }

    @PostMapping("/voting/submit")
    public CommonResponseBody<Void> submitVotingForm(@RequestBody PreopenVotingFormDto dto, KarrotAuthenticationToken authentication) {
        PreopenVotingForm preOpenVotingForm = dto.toEntity();

        // Check duplication
        PreopenVotingForm duplicatedData = preopenRepository.findById(preOpenVotingForm.getKarrotId()).orElse(null);
        ;
        if (duplicatedData != null) {
            throw new DuplicatedRequestException("이미 알림톡 신청한 유저. 데이터 이미 있음.", "이미 알림톡을 신청하셨어요! 서비스가 오픈하면 알림톡으로 알려드릴게요!");
        }

        preOpenVotingForm.setUser(authentication.getPrincipal().toEntity());
        preopenRepository.save(preOpenVotingForm);

        return CommonResponseBody.<Void>builder()
                .success()
                .build();
    }
}