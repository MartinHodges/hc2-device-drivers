package com.robocrank.hclightprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.robocrank.hcdevice.Health;
import com.robocrank.hcprocessor.EventPayload;
import com.robocrank.hcprocessor.HcProcessor;
import com.robocrank.hcprocessor.HcProcessorStatus;
import com.robocrank.hcprocessor.ProcessorEvent;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcLightProcessor implements HcProcessor {

	HcLightProcessorStatus status = new HcLightProcessorStatus();
	HashMap<String, String> config = new HashMap<String, String>();
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public List<String> getEventRegistrations() {
		return new ArrayList<String>(Arrays.asList("LIGHT_UPDATE"));
	}

	@Override
	public List<ProcessorEvent> processEvent(ProcessorEvent statusUpdate) {
		log.debug("Processing light change");
		double limit = Float.valueOf(config.get("limit"));
		ProcessorEvent response = new ProcessorEvent("DAY_TIME", new EventPayload("Processor", "127.0.0.1", Health.OK, 0,0.0));
		if (statusUpdate.getPayload().getRealMeasurement() < limit) {
			log.info("It is dark");
			status.setLight(false);
			status.setHealth(Health.OK);
			response.getPayload().setStatus(0);
		} else {
			log.info("It is light");
			status.setLight(true);
			status.setHealth(Health.OK);
			response.getPayload().setStatus(1);
		}
		ArrayList<ProcessorEvent> responses = new ArrayList<ProcessorEvent>(Arrays.asList(response));
		return responses;
	}

	@Override
	public HcProcessorStatus getStatus() {
		return status;
	}
	
	@Override
	public void addConfig(HashMap<String, String> config) {
		this.config = config;
		
	}
}
