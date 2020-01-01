package com.robocrank.hcdevice;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DeviceStatus {

	@Id
	private String address = "Unknown";
	private String ipAddress = "Unknown";
	private Health statusHealth = Health.UNKNOWN;
	private int powerHealth = 0;
	private int status = 0;
	private double realMeasurement = 0.0;
	private boolean enabled = true;
	private boolean locked = false;
	private String rawUpdate = "";
	private String eventType = "None";

	public DeviceStatus(String address,	String ipAddress, Health statusHealth, int powerHealth, int status, double realMeasurement, boolean enabled, boolean locked, String eventType, String rawUpdate) {
		this.address = address;
		this.ipAddress = ipAddress;
		this.powerHealth = powerHealth;
		this.statusHealth = statusHealth;
		this.status = status;
		this.realMeasurement = realMeasurement;
		this.enabled = enabled;
		this.locked = locked;
		this.eventType = eventType;
		this.rawUpdate = rawUpdate;
	}

}