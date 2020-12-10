package com.kw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kw.dao")
public class KwGenApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwGenApplication.class, args);
	}
}
