package com.team.cobi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스프링부트 스케쥴러 추가
@SpringBootApplication
public class TeamcobiErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamcobiErpApplication.class, args);
    }

}
