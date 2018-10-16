/**
 * 
 */
package com.aric.mlwrapper.domain;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author dursun
 *
 */
public class Pipeline extends ResourceSupport {
	private final String name;
	private final PipelineSchedule pipelineSchedule;

	@JsonCreator
	public Pipeline(@JsonProperty("name") final String name, @JsonProperty("schedule") final PipelineSchedule pipelineSchedule) {
		this.name = name;
		this.pipelineSchedule = pipelineSchedule;
	}
	
	public Pipeline(String name) {
		this.name = name;
		//TODO there should be a default pipelineSchedule.
		this.pipelineSchedule = null;
	}

	public String getName() {
		return name;
	}

	public PipelineSchedule getPipelineSchedule() {
		return pipelineSchedule;
	}
	
}
