package com.aric.mlwrapper.service;

import org.springframework.stereotype.Service;

import com.aric.mlwrapper.domain.Pipeline;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineService {

	public Pipeline getPipeline(String name) {
		// TODO Auto-generated method stub
		return new Pipeline(name, "", "run.sh");
	}

	public void startTrainingPipeline(Pipeline pipeline) {
		//TODO do the scheduling with pipeline.getPipelineSchedule()
	}

}
