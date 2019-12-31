package com.robocrank.hcprocessor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProcessorEvent {
	private String eventType;
	private EventPayload payload;
}
