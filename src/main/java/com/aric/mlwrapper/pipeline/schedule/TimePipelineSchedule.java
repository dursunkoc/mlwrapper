package com.aric.mlwrapper.pipeline.schedule;

import java.util.concurrent.TimeUnit;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class TimePipelineSchedule implements PipelineSchedule{
	private final long refreshTime;
	private final TimeUnit timeUnit;
	
	@JsonCreator
	public TimePipelineSchedule(
			@JsonProperty("refreshTime") final long refreshTime,
			@JsonProperty("timeUnit") final TimeUnit timeUnit
			) {
		this.refreshTime = refreshTime;
		this.timeUnit = timeUnit;
	}
	
	public long getRefreshTime() {
		return refreshTime;
	}
	
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("refreshTime", refreshTime)
				.append("timeUnit", timeUnit)
				.toString();
	}

}
