package com.aric.mlwrapper;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MlwrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(MlwrapperApplication.class, args);
	}
	
	@Bean
	public WatchService deploymentWatch() throws IOException {
		return FileSystems.getDefault().newWatchService();
	}
}
