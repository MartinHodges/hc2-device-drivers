package com.robocrank.hcprocessor;

import java.io.Serializable;

import com.robocrank.hcdevice.Health;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventPayload implements Serializable {

	private static final long serialVersionUID = 6040231276975407434L;

	private String address;
	private String ipAddress;
	private int powerHealth;
	private Health statusHealth;
	private int status;
	private double realMeasurement;
}
