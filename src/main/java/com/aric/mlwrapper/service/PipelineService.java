package com.aric.mlwrapper.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aric.mlwrapper.pipeline.Pipeline;
import com.aric.mlwrapper.pipeline.PipelineStatus;
import com.aric.mlwrapper.pipeline.RunnablePipeline;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PipelineService.class);
	private static final ObjectMapper OM = new ObjectMapper();

	private final Map<Path, Pipeline> pipelines = new HashMap<>();

	@Value("#{T(java.nio.file.Paths).get('${mlwrapper.deployments}')}")
	private Path deploymentPath;

	public Optional<Pipeline> getPipeline(String name) {
		return pipelines.values().stream().filter(p -> p.getName() == name).findFirst();
	}

	public void startPipeline(Pipeline pipeline) {
		LOGGER.debug("Starting {}", pipeline);
		pipeline.setPipelineStatus(PipelineStatus.STARTED);
	}
	
	public Stream<Pipeline> getStartedPipelines(){
		return pipelines.values().stream().filter(Pipeline::isStarted);
	}

	public void stopPipeline(Pipeline pipeline) {
		Process process = pipeline.getProcess();
		while (process.isAlive()) {
			// FIXME avoid infinite Loop
			process.destroy();
		}
	}

	public void createPipeline(Path path) {
		Path pipelineFile = deploymentPath.resolve(path);
		LOGGER.debug("Loading {}", pipelineFile);
		dropPipelineIfExists(pipelineFile);
		try {
			File file = deploymentPath.resolve(path).toFile();
			Pipeline pipeline = OM.readValue(file, Pipeline.class);
			this.startPipeline(pipeline);
			pipelines.put(pipelineFile, pipeline);
			LOGGER.info("Loaded {}", pipeline);
		} catch (IOException e) {
			LOGGER.error("Error processing {}.\n {}", pipelineFile, e);
		}
	}

	private void dropPipelineIfExists(Path path) {
		if (pipelines.containsKey(path)) {
			LOGGER.info("{} already exists!!", path);
			dropPipeline(path);
		}
	}

	public void dropPipeline(Path p) {
		Path pipelineFile = deploymentPath.resolve(p);
		LOGGER.debug("Unloading {}", pipelineFile);
		Pipeline removedPipeline = pipelines.remove(pipelineFile);
		this.stopPipeline(removedPipeline);
		LOGGER.info("Unloaded {}", removedPipeline);

	}

}
