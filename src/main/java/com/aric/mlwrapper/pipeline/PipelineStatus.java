package com.aric.mlwrapper.pipeline;

import java.util.Arrays;
import java.util.List;
/*
 * INITIALIZED -> WAITING
 * INITIALIZED -> TERMINATING
 * TRAINING -> WAITING
 * TRAINING -> TERMINATING
 * WAITING -> TRAINING
 * WAITING -> TERMINATING
 * TERMINATING -> STOPPED
 * STOPPED -> WAITING
 * TODO implement CQRS or COMMAND Pattern
 */

public enum PipelineStatus {
	INITIALIZED, WAITING, TRAINING, TERMINATING, STOPPED;

	// TO TRAINING
	private static final List<PipelineStatus> runnableStates = Arrays.asList(WAITING);
	// TO STARTED
	private static final List<PipelineStatus> startableStates = Arrays.asList(INITIALIZED, STOPPED);
	// TO TERMINATING
	private static final List<PipelineStatus> terminatableStates = Arrays.asList(INITIALIZED, TRAINING,
			WAITING);
	// TO WAITING
	private static final List<PipelineStatus> sustainableStates = Arrays.asList(TRAINING);
	// TO STOPABLE
	private static final List<PipelineStatus> stopableStates = Arrays.asList(TERMINATING);

	public static List<PipelineStatus> runnableStates() {
		return runnableStates;
	}

	public static List<PipelineStatus> startableStates() {
		return startableStates;
	}

	public static List<PipelineStatus> terminatableStates() {
		return terminatableStates;
	}

	public static List<PipelineStatus> sustainableStates() {
		return sustainableStates;
	}

	public static List<PipelineStatus> stopableStates() {
		return stopableStates;
	}

}
