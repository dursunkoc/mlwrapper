package com.aric.mlwrapper.pipeline.schedule;

import java.util.concurrent.TimeUnit;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dursun
 *
 */
public class TimeScheduleInfo implements ScheduleInfo{
	private final long refreshTime;
	private final TimeUnit timeUnit;
	private final long refreshTimeInMillis;
    private long timestamp;
    private long nextTimestamp;
	

	
	@JsonCreator
	public TimeScheduleInfo(
			@JsonProperty("refreshTime") final long refreshTime,
			@JsonProperty("timeUnit") final TimeUnit timeUnit
			) {
		this.refreshTime = refreshTime;
		this.timeUnit = timeUnit;
		switch (this.timeUnit) {
		case MILLISECONDS:
			this.refreshTimeInMillis = refreshTime;
			break;
		case SECONDS:
			this.refreshTimeInMillis = refreshTime * 1000;
			break;
		case MINUTES:
			this.refreshTimeInMillis = refreshTime * 60000;
			break;
		case HOURS:
			this.refreshTimeInMillis = refreshTime * 3600000;
			break;
		case DAYS:
			this.refreshTimeInMillis = refreshTime * 86400000;
			break;
		default:
			throw new IllegalArgumentException("Unsupported Timeunit!");
		}
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		this.nextTimestamp = this.timestamp + refreshTimeInMillis; 
	}
	
	public long getRefreshTime() {
		return refreshTime;
	}
	
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}
	
	@Override
	public boolean shouldExecute() {
		return System.currentTimeMillis() >= this.nextTimestamp;
	}

	@Override
	public void complete() {
		this.setTimestamp(System.currentTimeMillis());
	}
	
	@Override
	public void terminate() {
		this.complete();
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("refreshTime", refreshTime)
				.append("timeUnit", timeUnit)
				.append("refreshTimeInMillis", refreshTimeInMillis)
				.append("timestamp", timestamp)
				.append("nextTimestamp", nextTimestamp)
				.toString();
	}

}
