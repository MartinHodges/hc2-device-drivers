package com.robocrank.hcdevice;

import java.util.HashMap;

import lombok.Data;

@Data
public class DeviceConfig {

	private String address;
	private String ipAddress;
	private boolean enabled = false;
	private boolean locked = true;	
	private HashMap<String, String> config;
}
