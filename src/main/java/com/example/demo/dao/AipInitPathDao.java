package com.example.demo.dao;

import com.example.demo.Entity.AipInitPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * @author cdx
 * @date 2018/9/10
 */
@Repository
public class AipInitPathDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AipInitPath getAllByUsePlace(String usePlace) {
        AipInitPath aipInitPath = null;
        String sql = " select * from aip_init_path where usePlace = ? ";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, usePlace);
        while (rows.next()) {
            aipInitPath = new AipInitPath();
            aipInitPath.setFfmpeg_path(rows.getString("ffmpeg_path"));
            aipInitPath.setDirPath(rows.getString("dirPath"));
        }
        return aipInitPath;
    }

}
