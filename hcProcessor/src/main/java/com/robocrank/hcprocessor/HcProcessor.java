package com.robocrank.hcprocessor;

import java.util.HashMap;
import java.util.List;

public interface HcProcessor {

	public List<String> getEventRegistrations();

	public List<ProcessorEvent> processEvent(ProcessorEvent event);

	public HcProcessorStatus getStatus();
	
	public void addConfig(HashMap<String, String> config);
}
