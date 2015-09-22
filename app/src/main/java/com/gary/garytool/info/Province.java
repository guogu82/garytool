package com.gary.garytool.info;

import java.util.List;

/**
 * Created by Administrator on 2015/9/22.
 */
public class Province {
    private boolean success;
    private int error_code;
    private String message;
    private List<ProvinceList> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProvinceList> getData() {
        return data;
    }

    public void setData(List<ProvinceList> data) {
        this.data = data;
    }
}
