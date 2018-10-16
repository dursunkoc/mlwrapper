package com.aric.mlwrapper;

import java.util.concurrent.TimeUnit;

import com.aric.mlwrapper.domain.Pipeline;
import com.aric.mlwrapper.domain.TimePipelineSchedule;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Rocker {

	public static void main(String[] args) throws Exception {
		TimePipelineSchedule timePipelineSchedule = new TimePipelineSchedule(1000, TimeUnit.SECONDS);
		Pipeline pipeline = new Pipeline("Deneme", timePipelineSchedule);
		ObjectMapper om = new ObjectMapper();
		String jstr = om.writeValueAsString(pipeline);
		System.out.println(jstr);
		
		String json1 = "{\"name\":\"Deneme1\",\"pipelineSchedule\":{\"@type\" : \"TimePipelineSchedule\", \"refreshTime\":1000,\"timeUnit\":\"SECONDS\"},\"links\":[]}";
		Pipeline pipeline1 = om.readValue(json1, Pipeline.class);
		System.out.println(om.writeValueAsString(pipeline1));
		
		
		String json2 = "{\"name\":\"Deneme2\",\"pipelineSchedule\":{\"@type\" : \"SizePipelineSchedule\",\"initial\":1000,\"frequency\":10,\"range\":1000},\"links\":[]}";
		Pipeline pipeline2 = om.readValue(json2, Pipeline.class);
		System.out.println(om.writeValueAsString(pipeline2));
	}

}
