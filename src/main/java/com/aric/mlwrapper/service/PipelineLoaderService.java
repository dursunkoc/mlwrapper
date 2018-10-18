package com.aric.mlwrapper.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.aric.mlwrapper.domain.Pipeline;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineLoaderService implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(PipelineLoaderService.class);

	private static final ObjectMapper OM = new ObjectMapper();
	
	private final Map<String, Pipeline> pipelines= new HashMap<>();

	@Value("${mlwrapper.deployments}")
	private String deploymentPathUrl;

	@Autowired
	private WatchService deploymentWatch;

	private Path deploymentPath;

	@Override
	public void afterPropertiesSet() throws Exception {
		deploymentPath = Paths.get(deploymentPathUrl);
		deploymentPath.register(deploymentWatch, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
		Arrays.stream(deploymentPath.toFile().listFiles())
		.map(f->f.toPath())
		.filter(p->p.toString().endsWith(".json"))
		.forEach(this::createPipeline);
		
	}

	@Scheduled(fixedDelay = 2000)
	public void loadpipelines() throws Exception {
		LOGGER.debug("Checking started.");
		WatchKey watchKey = deploymentWatch.poll(10, TimeUnit.SECONDS);
		if (watchKey != null) {
			List<WatchEvent<?>> pollEvents = watchKey.pollEvents();

			pollEvents.stream()
					.filter(watchEvent -> ((Path) watchEvent.context()).toString().endsWith(".json"))
					.forEach(this::handlePipelineChange);

			LOGGER.debug("resetting.");
			boolean reset = watchKey.reset();
			if(!reset) {
				LOGGER.debug("reset failed.");
				while(!watchKey.reset()) {
					LOGGER.debug("reset failed...");
					Thread.sleep(1000);
				}
			}
		}
		LOGGER.debug("Checking ended.");
	}

	private void handlePipelineChange(WatchEvent<?> watchEvent) {
		LOGGER.info("handeling {} event.",watchEvent.kind().name());
		switch (watchEvent.kind().name()) {
		case "ENTRY_CREATE":
		case "ENTRY_MODIFY":
			createPipeline((Path) watchEvent.context());
			break;
		case "ENTRY_DELETE":
			dropPipeline((Path) watchEvent.context());
			break;
		default:
			unknownEvent(watchEvent);
			break;
		}
	}

	private void unknownEvent(WatchEvent<?> watchEvent) {
		Path p = (Path) watchEvent.context();
		String pipelineFile = deploymentPath.resolve(p).toString();
		LOGGER.error("Unknown event {} for {}", watchEvent.kind(), pipelineFile);
	}

	private void createPipeline(Path p) {
		String pipelineFile = deploymentPath.resolve(p).toString();
		LOGGER.debug("Loading {}", pipelineFile);
		if(pipelines.containsKey(pipelineFile)) {
			LOGGER.info("{} already exists!!", pipelineFile);
			dropPipeline(p);
		}
		try {
			File file = deploymentPath.resolve(p).toFile();
			Pipeline pipeline = OM.readValue(file, Pipeline.class);
			//TODO start pipeline
			pipelines.put(pipelineFile, pipeline);
			LOGGER.info("Loaded {}", pipeline);
		} catch (IOException e) {
			LOGGER.error("Error processing {}.\n {}", pipelineFile, e);
		}
	}

	private void dropPipeline(Path p) {
		String pipelineFile = deploymentPath.resolve(p).toString();
		LOGGER.debug("Unloading {}", pipelineFile);
		Pipeline removedPipeline = pipelines.remove(pipelineFile);
		//TODO stop pipeline
		LOGGER.info("Unloaded {}", removedPipeline);
		
	}

}
