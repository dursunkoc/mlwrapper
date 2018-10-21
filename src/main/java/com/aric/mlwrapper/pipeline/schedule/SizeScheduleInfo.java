package com.aric.mlwrapper.pipeline.schedule;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class SizeScheduleInfo implements ScheduleInfo {
	private final long initial;
	private final long frequency;
	private final long range;

	@JsonCreator
	public SizeScheduleInfo(
			@JsonProperty("initial") final long initial,
			@JsonProperty("frequency") final long frequency, 
			@JsonProperty("range") final long range) {
		this.initial = initial;
		this.frequency = frequency;
		this.range = range;
	}

	public long getInitial() {
		return initial;
	}

	public long getFrequency() {
		return frequency;
	}

	public long getRange() {
		return range;
	}
	
	@Override
	public boolean shouldExecute() {
		// TODO implement should execute for size schedule
		return false;
	}

	@Override
	public void complete() {
		// TODO implement complete for size schedule
		
	}

	@Override
	public void terminate() {
		// TODO implement terminate for size schedule
		
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("initial", initial)
				.append("frequency", frequency)
				.append("range", range)
				.toString();
	}

}
