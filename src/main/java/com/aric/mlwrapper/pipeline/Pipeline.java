/**
 * 
 */
package com.aric.mlwrapper.pipeline;

import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import com.aric.mlwrapper.pipeline.schedule.PipelineSchedule;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class Pipeline extends ResourceSupport{
	private final String name;
	private final String dataTopic;
	private final PipelineSchedule pipelineSchedule;
	private final String scriptPath;
	private final PipelinePlatform pipelinePlatform;
	private PipelineStatus pipelineStatus = PipelineStatus.INITIALIZED;
	private Process process;

	@JsonCreator
	public Pipeline(@JsonProperty(value = "name", required = true) final String name,
			@JsonProperty(value = "dataTopic", required = true) final String dataTopic,
			@JsonProperty(value = "scriptPath", required = true) final String scriptPath,
			@JsonProperty(value = "schedule", required = true) final PipelineSchedule pipelineSchedule,
			@JsonProperty(value = "platform", required = true) final PipelinePlatform pipelinePlatform) {
		this.name = name;
		this.dataTopic = dataTopic;
		this.scriptPath = scriptPath;
		this.pipelinePlatform = pipelinePlatform;
		this.pipelineSchedule = pipelineSchedule;
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

	public PipelineSchedule getPipelineSchedule() {
		return pipelineSchedule;
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
	
	public boolean isStarted() {
		return this.pipelineStatus == PipelineStatus.STARTED;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("name", name)
				.append("dataTopic", dataTopic)
				.append("scriptPath", scriptPath)
				.append("pipelineSchedule", pipelineSchedule)
				.append("pipelineStatus", pipelineStatus)
				.append("pipelinePlatform", pipelinePlatform)
				.append("super", super.toString())
			.toString();
	}

}
