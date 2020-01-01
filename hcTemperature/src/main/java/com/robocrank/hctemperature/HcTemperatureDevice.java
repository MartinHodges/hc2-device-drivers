package com.robocrank.hctemperature;

import com.robocrank.hcdevice.DeviceStatus;
import com.robocrank.hcdevice.HcDeviceDriver;
import com.robocrank.hcdevice.Health;
import com.robocrank.hcdevice.RawStateDto;
import com.robocrank.hcdevice.RawStatusException;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcTemperatureDevice extends HcDeviceDriver<RawStateDto> {

	@Override
	public DeviceStatus statusUpdate(String reportedStatus, String ipAddress) {
		try {
			this.ipAddress = ipAddress;
			convertRaw(reportedStatus);
			status.setRealMeasurement((double)status.getStatus()/10.0);
			// act as proxy - send to original HomeCentre server 
			send("homecentre.b30","8080","HomeCentre/TempStatus","battery=2200&address="+config.getAddress()+"&temp="+status.getStatus());
		} catch (RawStatusException e) {
			log.debug("Could not read status update: "+reportedStatus+" for "+config.toString()+" due to "+e.getMessage());
			status.setStatusHealth(Health.BAD_DATA);
		}
		return status;
	}


}
