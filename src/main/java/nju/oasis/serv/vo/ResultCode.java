package nju.oasis.serv.vo;

public enum ResultCode {
    SUCCESS(0, "success"),
    PARAM_ERROR(1001, "参数错误");

    private Integer code;
    private String  message;
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer code() {
        return this.code;
    }
    public String message(){
        return this.message;
    }
}
