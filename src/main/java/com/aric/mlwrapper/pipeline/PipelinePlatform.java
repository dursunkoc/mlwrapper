/**
 * 
 */
package com.aric.mlwrapper.pipeline;

/**
 * @author TTDKOC
 *
 */
public enum PipelinePlatform {
	PYTHON("python"), JAVA("java");
	private final String processName;

	private PipelinePlatform(final String processName) {
		this.processName = processName;
	}

	public String processName() {
		return this.processName;
	}
}
