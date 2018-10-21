/**
 * 
 */
package com.aric.mlwrapper.pipeline.schedule;

/**
 * @author TTDKOC
 *
 */
public class DefaultScheduleInfo implements ScheduleInfo {

	@Override
	public String toString() {
		return "DefaultScheduleInfo";
	}

	@Override
	public boolean shouldExecute() {
		// TODO implement should execute for default schedule
		return false;
	}

	@Override
	public void complete() {
		// TODO implement complete for default schedule
		
	}

	@Override
	public void terminate() {
		// TODO implement terminate for default schedule
		
	}
}
