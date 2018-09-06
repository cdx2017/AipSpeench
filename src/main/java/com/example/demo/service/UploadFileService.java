package com.example.demo.service;

import com.example.demo.entity.LearnFile;
import com.example.demo.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by DX on 2018/9/5.
 */
@Service
public class UploadFileService {
    private static Boolean flag = false;
    @Autowired
    UploadUtil uploadUtil;

    public Boolean uploadFile(LearnFile lf, HttpServletRequest request) {
        try {
            uploadUtil.uplodeFile(lf, request);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
