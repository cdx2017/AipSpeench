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
    private String usePlace;//key
    private String ffmpeg_path;//ffmpeg bin path
    private String dirPath;//the dir for upload_file and your_file to save
}
