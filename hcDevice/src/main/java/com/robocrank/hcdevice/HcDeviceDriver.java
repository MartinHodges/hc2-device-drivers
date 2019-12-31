package com.robocrank.hcdevice;

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcDeviceDriver<T extends RawStateDto> {

	static final int POWER_HEALTH = 0;
	static final int STATUS_HEALTH = 1;
	static final int STATUS = 2;
	static final int MEASUREMENT = 3;
	
	protected DeviceConfig config;
	
	protected DeviceStatus status;
	
	protected String address;
	protected String ipAddress;
	
	
	private RestTemplate restTemplate;

	ObjectMapper mapper = new ObjectMapper();

	
	public void setRestTemplate(RestTemplate restTemplate) {
	
		this.restTemplate = restTemplate;
	}

	
	protected boolean send(String host, String port, String path, String params) {
	    
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<String> entity = new HttpEntity<String>("Control",headers);

	    String url = "http://"+host+":"+port+"/"+path+"?"+params;
	    try {
	    	ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		    return response.getStatusCode() == HttpStatus.OK;
	    } catch (Exception e) {
	    	log.warn("Send failed to: "+url+" due to: "+e.getMessage());
	    }
	    return false;
	}

	
	public DeviceStatus statusUpdate(String reportedStatus, String ipAddress) {

		this.ipAddress = ipAddress;
		
		return null;
	}
	
	
	public void output(String ipAddress, boolean enabled, boolean locked, int status, double value) {
	}

	protected void convertRaw(String reportedState) throws RawStatusException {
		
		String parts[] = reportedState.split(",");
		
		if (parts.length != 4) {
			log.warn("Could not parse reported status due to wrong number of components in request: " + parts.length);
			throw new RawStatusException("Could not parse reported status");
		}
		
		int powerHealth = Integer.valueOf(parts[POWER_HEALTH]);

		if (powerHealth < 0 || powerHealth > 9) {
			log.warn("Could not parse reported status due to invalid power health: " + parts[POWER_HEALTH]);
			throw new RawStatusException("Could not parse reported status");
		}
		
		Health statusHealth = Health.forString(parts[STATUS_HEALTH]);
		
		if (statusHealth == Health.ERROR) {
			log.warn("Could not parse reported status due to invalid status health: " + parts[STATUS_HEALTH]);
			throw new RawStatusException("Could not parse reported status");
		}
		
		int statusValue = Integer.valueOf(parts[STATUS]);
		
		double measurement = Double.valueOf(parts[MEASUREMENT]);
		
		status.setStatus(statusValue);
		status.setStatusHealth(statusHealth);
		status.setPowerHealth(powerHealth);
		status.setRealMeasurement(measurement);
		status.setRawUpdate(reportedState);
		status.setEventType("UPDATE");
		status.setAddress(address);
	}
	
	protected T convertRaw(String reportedState, RawStateDto obj) throws RawStatusException {
		try {
			log.debug("Received payload: "+reportedState );
			
			@SuppressWarnings("unchecked")
			T dto = (T) mapper.readValue(reportedState, obj.getClass());
			
			log.info("Parsed payload: "+dto.toString() );
			
			status.setStatusHealth(dto.getStatusHealth());
			status.setPowerHealth(dto.getPowerHealth());
			
			return dto;
		} catch (Exception e) {
			log.warn("Could not parse reported state: " + reportedState + "caused by "+e);
			throw new RawStatusException("Could not parse reported state: "+reportedState+ "caused by " + e);
		}
	}
	
	
	public void register(String address, DeviceConfig config) {
		
		this.config = config;
		this.status = new DeviceStatus();
		this.address = address;
	}
	
	public void deregister() {
		
		// TODO implement device de-registration
	}
}
