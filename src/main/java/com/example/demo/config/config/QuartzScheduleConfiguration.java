package com.example.demo.config.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.example.demo.infra.quartz.factory.AutowiringSpringBeanJobFactory;

/**
 * Quartz 排程配置類
 */
@Configuration
public class QuartzScheduleConfiguration {

	@Bean
	public AutowiringSpringBeanJobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, AutowiringSpringBeanJobFactory jobFactory) {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJobFactory(jobFactory);

		// 建立 Properties 物件來承接叢集設定
		Properties properties = new Properties();

		// --- 分布式核心配置 ---
		// 必須設為 true
		properties.put("org.quartz.jobStore.isClustered", "true");
		// 必須設為 AUTO，讓各節點自動產生唯一 ID (如: DESKTOP-NICK_171487...)
		properties.put("org.quartz.scheduler.instanceId", "AUTO");
		// 叢集檢查心跳間隔 (建議 10-20 秒)
		properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
		// 資料庫驅動委託類
		properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		// 設置 AutowiringSpringBeanJobFactory，使其受框架管理
		factory.setQuartzProperties(properties);
		return factory;
	}

}