/**
 * 
 */
package com.aric.mlwrapper.pipeline;

import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import com.aric.mlwrapper.pipeline.schedule.ScheduleInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class Pipeline extends ResourceSupport{
	private final String name;
	private final String dataTopic;
	private final ScheduleInfo scheduleInfo;
	private final String scriptPath;
	private final PipelinePlatform pipelinePlatform;
	private PipelineStatus pipelineStatus = PipelineStatus.INITIALIZED;
	private Process process;

	@JsonCreator
	public Pipeline(@JsonProperty(value = "name", required = true) final String name,
			@JsonProperty(value = "dataTopic", required = true) final String dataTopic,
			@JsonProperty(value = "scriptPath", required = true) final String scriptPath,
			@JsonProperty(value = "scheduleInfo", required = true) final ScheduleInfo scheduleInfo,
			@JsonProperty(value = "platform", required = true) final PipelinePlatform pipelinePlatform) {
		this.name = name;
		this.dataTopic = dataTopic;
		this.scriptPath = scriptPath;
		this.pipelinePlatform = pipelinePlatform;
		this.scheduleInfo = scheduleInfo;
	}
	
	public void setPipelineStatus(PipelineStatus pipelineStatus) {
		this.pipelineStatus = pipelineStatus;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}

	public String getName() {
		return name;
	}

	public ScheduleInfo getScheduleInfo() {
		return scheduleInfo;
	}

	public String getDataTopic() {
		return dataTopic;
	}

	public String getScriptPath() {
		return scriptPath;
	}

	public PipelineStatus getPipelineStatus() {
		return pipelineStatus;
	}

	public PipelinePlatform getPipelinePlatform() {
		return pipelinePlatform;
	}
	
	public Process getProcess() {
		return process;
	}
		
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("name", name)
				.append("dataTopic", dataTopic)
				.append("scriptPath", scriptPath)
				.append("scheduleInfo", scheduleInfo)
				.append("pipelineStatus", pipelineStatus)
				.append("pipelinePlatform", pipelinePlatform)
				.append("super", super.toString())
			.toString();
	}

	/**
	 * @return
	 */
	public boolean shouldExecute() {
		return this.isRunnable() && this.scheduleInfo.shouldExecute();
	}
	
	private boolean isRunnable() {
		return PipelineStatus.runnableStates().contains(this.pipelineStatus);
	}

	public boolean isSustainable() {
		return PipelineStatus.sustainableStates().contains(this.pipelineStatus);
	}
	
	public boolean isWaiting() {
		return this.pipelineStatus == PipelineStatus.WAITING;
	}

	public boolean isTraining() {
		return this.pipelineStatus == PipelineStatus.TRAINING;
	}

	public boolean isTerminating() {
		return this.pipelineStatus == PipelineStatus.TERMINATING;
	}

	public boolean isProcessCompleted() {
		return this.process == null || !this.process.isAlive();
	}

	public void killProcess() {
		Process process = this.getProcess();
		while (process!=null && process.isAlive()) {
			// FIXME avoid infinite Loop
			process.destroy();
		}		
	}


}
