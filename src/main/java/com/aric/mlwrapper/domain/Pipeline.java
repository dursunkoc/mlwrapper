/**
 * 
 */
package com.aric.mlwrapper.domain;

import org.springframework.core.style.ToStringCreator;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class Pipeline extends ResourceSupport {
	private final String name;
	private final String dataTopic;
	private final PipelineSchedule pipelineSchedule;
	private final String scriptPath;

	@JsonCreator
	public Pipeline(@JsonProperty(value = "name", required = true) final String name,
			@JsonProperty(value = "dataTopic", required = true) final String dataTopic,
			@JsonProperty(value = "scriptPath", required = true) final String scriptPath,
			@JsonProperty(value = "schedule") final PipelineSchedule pipelineSchedule) {
		this.name = name;
		this.dataTopic = dataTopic;
		this.scriptPath= scriptPath;
		if (pipelineSchedule != null) {
			this.pipelineSchedule = pipelineSchedule;
		}else {
			this.pipelineSchedule = new DefaultPipelineSchedule();
		}
	}

	public Pipeline(final String name, final String dataTopic, final String scriptPath) {
		this.name = name;
		this.dataTopic = dataTopic;
		this.scriptPath = scriptPath;
		this.pipelineSchedule = new DefaultPipelineSchedule();
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
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("name", name)
				.append("dataTopic", dataTopic)
				.append("scriptPath", scriptPath)
				.append("pipelineSchedule", pipelineSchedule)
				.append("super", super.toString())
				.toString();
	}
}
