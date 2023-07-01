package com.bovine.taotao.admin.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理系统启动入口
 * @author eden
 * @date 2023年7月1日 下午10:15:04
 */
@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = "com.bovine.taotao.admin.web.mapper")
public class AdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminWebApplication.class, args);
	}

}
