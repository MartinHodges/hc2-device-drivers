package com.robocrank.hcpresence;

import com.robocrank.hcdevice.DeviceStatus;
import com.robocrank.hcdevice.HcDeviceDriver;
import com.robocrank.hcdevice.Health;
import com.robocrank.hcdevice.RawStateDto;
import com.robocrank.hcdevice.RawStatusException;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcPresenceDevice extends HcDeviceDriver<RawStateDto> {

	@Override
	public DeviceStatus statusUpdate(String reportedStatus, String ipAddress) {
		try {
			this.ipAddress = ipAddress;
			convertRaw(reportedStatus);

			if (status.getStatus() == 0) {
				log.debug("Presence cleared");
			} else {
				log.debug("Presence detected");
			}
		} catch (RawStatusException e) {
			log.debug("Could not read status update: "+reportedStatus+" for "+config.toString()+" due to "+e.getMessage());
			status.setStatusHealth(Health.BAD_DATA);
		}
		return status;
	}
}
