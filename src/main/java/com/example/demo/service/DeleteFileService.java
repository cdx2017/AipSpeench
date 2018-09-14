package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by DX on 2018/9/14.
 */
@Service
public class DeleteFileService {

    /**
     * @param filePath 文件或者目录路径
     * @return Boolean
     */
    public Boolean delTempChild(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("The file: " + filePath + " is deleted!");
            return true;
        }
        if (file.isDirectory()) {
            recurDelete(file);
            System.out.println("The Directory: " + filePath + " is deleted!");
            return true;
        }
        return false;
    }

    public static void recurDelete(File f) {
        try {
            for (File fi : f.listFiles()) {
                if (fi.isDirectory()) {
                    recurDelete(fi);
                } else {
                    fi.delete();
                }
            }
            f.delete();
        } catch (NullPointerException n) {
            System.out.println("Sorry,No such file");
        }
    }

   /* public static void main(String[] args) {
        DeleteFileService deleteFileService=new DeleteFileService();
        deleteFileService.delTempChild("C:\\\\Users\\\\DX\\\\Desktop\\\\music\\\\use1.wav");
    }*/

}
