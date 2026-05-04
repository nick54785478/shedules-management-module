package com.example.demo.infra.adapter;

import org.springframework.stereotype.Component;

import com.example.demo.application.domain.schedule.aggregate.vo.CronExpression;
import com.example.demo.application.port.CronParserPort;
import com.example.demo.application.shared.exception.InvalidCronException;

@Component
class CronParserAdapter implements CronParserPort {
	
	@Override
	public CronExpression parse(String expression) {
		// 1. 呼叫 Quartz 技術庫進行深度校驗
		if (!org.quartz.CronExpression.isValidExpression(expression)) {
			throw new InvalidCronException("無效的 Quartz Cron 格式: " + expression);
		}

		// 2. 驗證通過，產出領域物件
		return new CronExpression(expression);
	}
}