package com.example.demo.application.shared.view;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

/**
 * 用於展示的視圖 (原本的 ScheduleJob)
 */
@Data
@Builder
public class ScheduleJobView {

	// ### 來自 DB 的資料 ###
	private String jobId; // 領域識別碼

	private String jobType; // 任務類型 (Bean Name)

	private String description; // 任務描述 (如果有)

	// ### 來自 Quartz 與 DB 共有 ###
	private String name;

	private String group;

	// ### 來自 Quartz 運行時 ###
	private String cronExpression;

	private String triggerType;

	private Integer intervalInSeconds;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date nextFireTime;

	private String state; // Quartz 狀態 (NORMAL, PAUSED, ERROR 等)

	private String domainStatus; // DB 記錄的業務狀態
}