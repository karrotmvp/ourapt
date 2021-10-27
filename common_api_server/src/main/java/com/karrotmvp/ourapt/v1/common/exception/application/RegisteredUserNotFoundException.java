package com.karrotmvp.ourapt.v1.common.exception.application;

import com.karrotmvp.ourapt.v1.common.constant.ApiResult;

public class RegisteredUserNotFoundException extends AbstractWebApplicationContextException {
    public RegisteredUserNotFoundException() {
        super("등록된 유저 없음, 우리 서비스를 사용하지 않는 당근 유저임", "사용자 동의를 통한 등록을 먼저 진행해주세요.");
    }

    @Override
    public ApiResult getApiResult() {
        return ApiResult.REGISTERED_USER_NOT_FOUND;
    }
}
