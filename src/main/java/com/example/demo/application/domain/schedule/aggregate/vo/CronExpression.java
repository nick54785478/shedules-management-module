package com.example.demo.application.domain.schedule.aggregate.vo;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CronExpression(String value) {
	// 這裡只做最基礎的非空檢查，複雜邏輯通通拿掉
	public CronExpression {
		Objects.requireNonNull(value, "Cron value cannot be null");
	}
}