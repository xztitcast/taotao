package com.bovine.taotao.setup.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class SetupWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SetupWebApplication.class, args);
	}

}
