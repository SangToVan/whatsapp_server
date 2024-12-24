package com.sangto.whatsapp.request;

public class SingleChatRequest {

    private Integer userId;

    public SingleChatRequest() {
    }

    public SingleChatRequest(Integer userId) {
        super();
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SingleChatRequest [userId=" + userId + "]";
    }
}
