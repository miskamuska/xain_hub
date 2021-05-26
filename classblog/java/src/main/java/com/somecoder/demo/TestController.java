package com.somecoder.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author zuozhiwei
 */
@RestController
public class TestController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/list")
    public List<Map<String, Object>> userList(
            int a
    ) {
        System.out.print(a+"");
        String sql = "select * from q_user";
        return jdbcTemplate.queryForList(sql);
    }
}
