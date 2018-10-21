package com.aric.mlwrapper.pipeline.schedule;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author dursun
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=TimeScheduleInfo.class, name = "TimeScheduleInfo"),
  @JsonSubTypes.Type(value=DefaultScheduleInfo.class, name = "DefaultScheduleInfo"),
  @JsonSubTypes.Type(value=SizeScheduleInfo.class, name = "SizeScheduleInfo")
})
public interface ScheduleInfo {
	public boolean shouldExecute();

	public void complete();

	public void terminate();
}
