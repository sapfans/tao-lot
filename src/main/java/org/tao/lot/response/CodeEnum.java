package org.tao.lot.response;

public enum CodeEnum {
    Success(200),
    CreateSuccess(201),
    AsynCreateSuccess(202),
    DeleteSuccess(204),
    Failure(400),
    AuthFailure(403);
    Integer code;

    public Integer getCode() {
        return code;
    }

    private CodeEnum(Integer code) {
        this.code = code;
    }
}
