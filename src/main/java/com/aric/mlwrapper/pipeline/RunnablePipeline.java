package com.aric.mlwrapper.pipeline;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunnablePipeline implements Runnable{
	private static final Logger LOG = LoggerFactory.getLogger(RunnablePipeline.class);
	private final Pipeline pipeline;
	
	public RunnablePipeline(final Pipeline pipeline) {
		this.pipeline = pipeline;
	}
	
	public static final RunnablePipeline fromPipeline(final Pipeline pipeline) {
		return new RunnablePipeline(pipeline);
	}
	
	@Override
	public void run() {
		try {
			ProcessBuilder processBuilder = buildProcess(pipeline);
			Process process = processBuilder.start();
			pipeline.setPipelineStatus(PipelineStatus.TRAINING);
			pipeline.setProcess(process);
		} catch (IOException e) {
			LOG.error(String.format("Failed to run pipeline %s", pipeline) ,e);
		}
	}
	
	private ProcessBuilder buildProcess(Pipeline pipeline) throws IOException {
		String scriptPath = pipeline.getScriptPath();
		Path path = Paths.get(scriptPath);
		ProcessBuilder processBuilder = new ProcessBuilder(pipeline.getPipelinePlatform().processName(), scriptPath);

		File errorFile = createProcessFile(path, ".err");
		processBuilder.redirectError(errorFile);

		File outFile = createProcessFile(path, ".out");
		processBuilder.redirectOutput(outFile);

		return processBuilder;
	}

	private File createProcessFile(Path path, String extension) throws IOException {
		String fileName = path.getFileName().toString();
		Path newPath = path.getParent().resolve(fileName + extension);

		if (!Files.deleteIfExists(newPath)) {
			throw new IOException(String.format("Unable to delete existing file \"%s\".", newPath.toString()));
		}
		return Files.createFile(newPath).toFile();
	}

}
