package com.bovine.taotao.admin.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后台管理系统启动入口
 * 设置中心服务消费者启动类
 * 使用dubbo框架注意事项
 * 1.序列化使用fastjson2, apache dubbo官方默认使用该序列化
 * 2.该序列化效率很高使用的是JSONB为了安全关闭了autoType自动序列化功能(该功能漏洞详见fastjson)
 * 3.当序列化对象没有无参构造时就会报auotype not support, 该类型错误就是为了安全关闭的autoType自动序列化功能, 比如服务端报SQL错误org.springframework.jdbc.BadSqlGrammarException
 * 4.由于dubbo在升级Spring boot3.0 + JDK17使用的验证方式还是javax包所以全局配置bootstrap.yml中设置validation: false, 所有入参校验均由Spring提供JSR规范校验
 * Spring boot3.x注意事项:
 * 1.data包下的配置已更改，比如redis以往为spring.redis.xxx更改为spring.data.redis.xxx
 * 2.starter自动化组件(SPI)加载配置变化, 以往在META-INFO/spring.factories更改为META-INFO/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
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
