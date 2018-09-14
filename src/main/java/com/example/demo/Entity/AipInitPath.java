package com.example.demo.Entity;

import lombok.Data;
import org.springframework.stereotype.Component;


/**
 * @author cdx
 * @date 2018/9/12
 */

@Data
@Component
public class AipInitPath {
    private String usePlace;//key 1
    private String username;//key 2
    private String password;//
    private String ffmpeg_path;//ffmpeg bin path
    private String dirPath;//the dir for upload_file and your_file to save
    private String nextgo;//next to go
}
