package com.bovine.taotao.sso.web;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 单点登录启动类
 * 使用dubbo框架注意事项
 * 1.序列化使用fastjson2, apache dubbo官方默认使用该序列化
 * 2.该序列化效率很高使用的是JSONB为了安全关闭了autoType自动序列化功能(该功能漏洞详见fastjson)
 * 3.当序列化对象没有无参构造时就会报auotype not support, 该类型错误就是为了安全关闭的autoType自动序列化功能, 比如服务端报SQL错误org.springframework.jdbc.BadSqlGrammarException
 * 4.由于dubbo在升级Spring boot3.0 + JDK17使用的验证方式还是javax包所以全局配置bootstrap.yml中设置validation: false, 所有入参校验均由Spring提供JSR规范校验
 */
@EnableDubbo
@SpringBootApplication
public class SsoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoWebApplication.class, args);
	}

}
