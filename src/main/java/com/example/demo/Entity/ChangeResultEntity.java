package com.example.demo.Entity;

import lombok.Data;

/**
 * Created by DX on 2018/9/10.
 */
@Data
public class ChangeResultEntity {

    public String result;
    public String err_msg;
    public String err_no;

    public ChangeResultEntity() {
    }

    public ChangeResultEntity(String result, String err_msg, String err_no) {
        this.result = result;
        this.err_msg = err_msg;
        this.err_no = err_no;
    }
}
