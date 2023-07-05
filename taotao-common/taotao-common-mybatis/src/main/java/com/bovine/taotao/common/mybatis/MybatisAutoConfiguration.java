package com.bovine.taotao.common.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.bovine.taotao.common.mybatis.handler.MybatisPlusMetaObjectHandler;

/**
 * mybatis-plus自动化配置
 *
 * mybatis-plus版本变化注意点:
 * 3.5.3只适用于Spring boot2.x
 * 3.5.3.1适用于Spring boot3.x
 * @author eden
 * @date 2023年07月05日 下午4:17:23
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
public class MybatisAutoConfiguration {

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
		interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
		return interceptor;
	}
	
	@Bean
	public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
		return new MybatisPlusMetaObjectHandler();
	}
}
