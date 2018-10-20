package com.aric.mlwrapper.pipeline.schedule;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/**
 * @author dursun
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=TimePipelineSchedule.class, name = "TimePipelineSchedule"),
  @JsonSubTypes.Type(value=TimePipelineSchedule.class, name = "DefaultPipelineSchedule"),
  @JsonSubTypes.Type(value=SizePipelineSchedule.class, name = "SizePipelineSchedule")
})
public interface PipelineSchedule {

}
