package com.karrotmvp.ourapt.v1.adminquestion;

import com.karrotmvp.ourapt.v1.common.CommonResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/admin-question")
public class AdminQuestionController {

    @GetMapping(value = "")
    public CommonResponseBody<Void> getAvailableAdminQuestion() {
        return CommonResponseBody.<Void>builder()
                .success().build();
    }
    // DB user 가 잇는지 없는지 확인하는거.. 해야하나?
    // 1. security에 위임한다.
    // 2. 그냥 accessToken이 잇다는건 동의 햇다는 뜻이니 그냥 믿고 조회한다.
}
