package com.example.demo.iface.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.application.service.ScheduledJobApplicationService;
import com.example.demo.application.shared.command.UpdateJobCronCommand;
import com.example.demo.application.shared.view.ScheduleJobView;
import com.example.demo.iface.dto.req.UpdateJobCronResource;
import com.example.demo.iface.dto.res.JobCronUpdatedResource;
import com.example.demo.iface.dto.res.ScheduleJobPausedResource;
import com.example.demo.iface.dto.res.ScheduleJobResumedResource;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/jobs")
public class ScheduleJobController {

	private ScheduledJobApplicationService applicationService;

	/**
	 * 暫停特定的排程
	 * 
	 * @throws Exception
	 */
	@PostMapping("/pause/{jobId}")
	public ResponseEntity<ScheduleJobPausedResource> pauseJob(@PathVariable String jobId) {
		applicationService.pauseTask(jobId);
		return new ResponseEntity<>(new ScheduleJobPausedResource("200", "Job paused"), HttpStatus.OK);
	}

	/**
	 * 重啟特定的排程
	 * 
	 * @throws Exception
	 */
	@PostMapping("/resume/{jobId}")
	public ResponseEntity<ScheduleJobResumedResource> resumeJob(@PathVariable String jobId) {
		applicationService.resumeTask(jobId);
		return new ResponseEntity<>(new ScheduleJobResumedResource("200", "Job resumed"), HttpStatus.OK);
	}

	/**
	 * 查詢系統內所有排程狀態
	 */
	@GetMapping("/status")
	public ResponseEntity<List<ScheduleJobView>> getJobStatus() {
		return new ResponseEntity<>(applicationService.getJobInfoResources(), HttpStatus.OK);
	}

	/**
	 * 更新特定排程的 cron
	 * */
	@PostMapping("/update-cron")
	public ResponseEntity<JobCronUpdatedResource> updateCron(@RequestBody UpdateJobCronResource request) {
		UpdateJobCronCommand command = new UpdateJobCronCommand(request.name(), request.group(), request.newCron());
		applicationService.updateJobCron(command);
		return ResponseEntity.ok(new JobCronUpdatedResource("200", "更新 cron 成功"));
	}
}
