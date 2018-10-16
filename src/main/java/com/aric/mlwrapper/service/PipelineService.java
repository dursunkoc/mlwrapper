package com.aric.mlwrapper.service;

import org.springframework.stereotype.Service;

import com.aric.mlwrapper.domain.Pipeline;

@Service
public class PipelineService {

	public Pipeline getPipeline(String name) {
		// TODO Auto-generated method stub
		return new Pipeline(name);
	}

	public void startTrainingPipeline(Pipeline pipeline) {
		// TODO Auto-generated method stub
		
		//TODO do the scheduling with pipeline.getPipelineSchedule()
	}

}
