package com.bovine.taotao.setup.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = {"com.bovine.taotao.setup.mapper"})
public class SetupServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SetupServiceApplication.class, args);
	}

}
