package com.dh.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001, "问题不存在。"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论回复。"),
    NO_LOGIN(2003, "未登录不能进行评论");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
