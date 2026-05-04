package com.example.demo.application.shared.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定義異常：代表 Cron 表達式不合法
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidCronException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -140380591178035163L;
	private final String expression;

	public InvalidCronException(String expression) {
		super(String.format("「%s」不是一個合法的 Cron 表達式，請檢查格式是否符合 Quartz 規範 (如：日與週不可同時指定)。", expression));
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}
}