package com.aric.mlwrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineExecutionService {
	
	@Autowired
	private PipelineService pipelineService;
	
	@Scheduled(fixedDelay=1000)
	public void checkPipelines() {
		pipelineService.getStartedPipelines().forEach(p->{
			
		});
	}
}
