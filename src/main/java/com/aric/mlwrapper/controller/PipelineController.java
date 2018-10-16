/**
 * 
 */
package com.aric.mlwrapper.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aric.mlwrapper.domain.Pipeline;
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
		Pipeline pipeline = pipelineService.getPipeline(name);
		pipeline.add(linkTo(methodOn(PipelineController.class).pipeline(name)).withSelfRel());
		return new ResponseEntity<Pipeline>(pipeline, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.PUT)
	public HttpEntity<Void> pipelineStart(@RequestParam(name = "name", required = true) String name) {
		Pipeline pipeline = pipelineService.getPipeline(name);
		pipelineService.startTrainingPipeline(pipeline);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
