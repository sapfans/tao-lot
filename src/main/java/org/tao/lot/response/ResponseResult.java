package org.tao.lot.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResult {

    private String  message = "success";
    private Integer code    = CodeEnum.Success.getCode();
    private String  debug;
    private Object  data;

    public ResponseResult() {

    }

    public ResponseResult(String status, String message) {
        this.message = message;
    }

    public static ResponseResult initResponse(String status, String message, Integer code) {
        ResponseResult respResult = new ResponseResult();
        respResult.setMessage(message);
        respResult.setCode(code);
        return respResult;
    }
}
