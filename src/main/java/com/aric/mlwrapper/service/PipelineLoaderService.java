package com.aric.mlwrapper.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author TTDKOC
 *
 */
@Service
public class PipelineLoaderService implements InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(PipelineLoaderService.class);

	@Value("${mlwrapper.deployments}")
	private String deploymentPathUrl;
	
	private Path deploymentPath;

	@Autowired
	private WatchService deploymentWatch;
	
	@Autowired
	private PipelineService pipelineService;

	@Override
	public void afterPropertiesSet() throws Exception {
		deploymentPath = Paths.get(deploymentPathUrl);
		deploymentPath.register(deploymentWatch, StandardWatchEventKinds.ENTRY_MODIFY,
				StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);
		Arrays.stream(deploymentPath.toFile().listFiles())
		.map(File::toPath)
		.filter(p->p.toString().endsWith(".json"))
		.forEach(pipelineService::createPipeline);
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
			pipelineService.createPipeline((Path) watchEvent.context());
			break;
		case "ENTRY_DELETE":
			pipelineService.dropPipeline((Path) watchEvent.context());
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
}
