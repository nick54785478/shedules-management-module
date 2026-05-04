package com.example.demo.application.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查無資料異常
 * */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JobNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853666251929306799L;

	public JobNotFoundException(String id) {
		super("找不到識別碼為 [" + id + "] 的排程任務");
	}
}