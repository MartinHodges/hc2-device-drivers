package com.robocrank.hclight;

import com.robocrank.hcdevice.DeviceStatus;
import com.robocrank.hcdevice.HcDeviceDriver;
import com.robocrank.hcdevice.Health;
import com.robocrank.hcdevice.RawStateDto;
import com.robocrank.hcdevice.RawStatusException;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcLightDevice extends HcDeviceDriver<RawStateDto> {

	@Override
	public void output(String ipAddress, boolean enabled, boolean locked, int status, double value) {
		boolean outputOn = status != 0;
		
		log.debug("Sending light device output "+outputOn);
		if (send(ipAddress, "80", "", "status="+((status == 0)?"0":"1"))) {
			log.info("Output successful");
		} else {
			log.info("Output failed");
		}
	}

	@Override
	public DeviceStatus statusUpdate(String reportedStatus, String ipAddress) {
		try {
			this.ipAddress = ipAddress;
			convertRaw(reportedStatus);
			// act as proxy - send to original HomeCentre server 
			send("homecentre.b30","8080","HomeCentre/LightStatus","light="+status.getRealMeasurement()+"&address="+config.getAddress());
		} catch (RawStatusException e) {
			log.debug("Could not read status update: "+reportedStatus+" for "+config.toString()+" due to "+e.getMessage());
			status.setStatusHealth(Health.BAD_DATA);
		}
		return status;
	}

}
