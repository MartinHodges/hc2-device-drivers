package com.robocrank.hcdaytimeprocessor;

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
public class HcDayTimeProcessor implements HcProcessor {

	ObjectMapper mapper = new ObjectMapper();
	
	HcDayTimeProcessorStatus status = new HcDayTimeProcessorStatus();
	HashMap<String, String> config = new HashMap<String, String>();
	
	@Override
	public List<String> getEventRegistrations() {
		return new ArrayList<String>(Arrays.asList("DAY_TIME"));
	}

	@Override
	public List<ProcessorEvent> processEvent(ProcessorEvent statusUpdate) {
		log.debug("Processing day time change");
		ProcessorEvent response = new ProcessorEvent("HC_CONTROL", new EventPayload("Outdoor Light Switch", "127.0.0.1", 9, Health.OK, 0,0.0));
		if (statusUpdate.getPayload().getStatus() == 0) {
			log.info("It is nighttime");
			status.setDaytime(false);
			status.setHealth(Health.OK);
			response.getPayload().setStatus(1);
		} else {
			log.info("It is daytime");
			status.setDaytime(true);
			status.setHealth(Health.OK);
			response.getPayload().setStatus(0);
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
