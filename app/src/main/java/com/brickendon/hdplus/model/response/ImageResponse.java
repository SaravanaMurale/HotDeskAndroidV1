package com.brickendon.hdplus.model.response;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("image")
    private String image;
    @SerializedName("message")
    private Message message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public class Message {
        @SerializedName("name")
        private String name;
        @SerializedName("message")
        private String message;
        @SerializedName("code")
        private String code;
        @SerializedName("requestId")
        private String requestId;
        @SerializedName("statusCode")
        private int statusCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }
    }
}
