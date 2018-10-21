package com.aric.mlwrapper.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aric.mlwrapper.pipeline.Pipeline;
import com.aric.mlwrapper.pipeline.RunnablePipeline;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineExecutionService {

	private final ExecutorService ES = Executors.newCachedThreadPool();

	@Autowired
	private PipelineService pipelineService;

	@Scheduled(fixedDelay = 100)
	public void schedulePipelines() {
		pipelineService.getWaitingPipelines()
			.filter(Pipeline::shouldExecute)
			.map(RunnablePipeline::fromPipeline)
			.forEach(ES::submit);
	}
	
	@Scheduled(fixedDelay = 100)
	public void houseKeeping() {
		pipelineService.getTrainingPipelines()
			.forEach(pipelineService::handleCompleted);
		pipelineService.getTerminatingPipelines()
			.forEach(pipelineService::stopPipeline);
	}
}
