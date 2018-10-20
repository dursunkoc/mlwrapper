/**
 * 
 */
package com.aric.mlwrapper.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aric.mlwrapper.pipeline.Pipeline;
import com.aric.mlwrapper.service.PipelineService;

/**
 * @author dursun
 *
 */
@RestController
@RequestMapping(path = "/pipeline")
public class PipelineController {

	@Autowired
	private PipelineService pipelineService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public HttpEntity<Pipeline> pipeline(@RequestParam(name = "name", required = true) String name) {
		Optional<Pipeline> pipelineOpt = pipelineService.getPipeline(name);
		if (pipelineOpt.isPresent()) {
			Pipeline pipeline = pipelineOpt.get();
			pipeline.add(linkTo(methodOn(PipelineController.class).pipeline(name)).withSelfRel());
			return new ResponseEntity<>(pipeline, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(path = "/start", method = RequestMethod.PUT)
	public HttpEntity<Void> pipelineStart(@RequestParam(name = "name", required = true) String name) {
		Optional<Pipeline> pipelineOpt = pipelineService.getPipeline(name);
		pipelineOpt.ifPresent(pipelineService::startPipeline);
		return new ResponseEntity<>(pipelineOpt.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@RequestMapping(path = "/stop", method = RequestMethod.PUT)
	public HttpEntity<Void> pipelineStop(@RequestParam(name = "name", required = true) String name) {
		Optional<Pipeline> pipelineOpt = pipelineService.getPipeline(name);
		pipelineOpt.ifPresent(pipelineService::stopPipeline);
		return new ResponseEntity<>(pipelineOpt.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

}
