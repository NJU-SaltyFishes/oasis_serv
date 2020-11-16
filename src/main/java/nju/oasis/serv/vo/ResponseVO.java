package nju.oasis.serv.vo;

import lombok.Data;

@Data
public class ResponseVO {
    private Integer status;
    private String message;
    private Object data;

    public static ResponseVO output(ResultCode resultCode, Object data){
        ResponseVO response = new ResponseVO();
        response.status = resultCode.code();
        response.message = resultCode.message();
        response.data = data;
        return response;
    }
}
