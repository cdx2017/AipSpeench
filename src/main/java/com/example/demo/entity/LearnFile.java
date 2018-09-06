package com.example.demo.entity;

import lombok.Data;

/**
 * 文件实体
 *
 * @author cdx
 */
@Data
public class LearnFile {
    //文件id
    private int fileid;
    //文件名
    private String filename;
    //文件上传地址
    private String fileurl;
    //文件上传时间
    private String uplodetime;

    public LearnFile() {
    }

    public LearnFile(int fileid, String filename, String fileurl, String uplodetime) {
        super();
        this.fileid = fileid;
        this.filename = filename;
        this.fileurl = fileurl;
        this.uplodetime = uplodetime;
    }

    public LearnFile(String filename, String fileurl, String uplodetime) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.uplodetime = uplodetime;
    }


}