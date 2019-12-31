package com.robocrank.hcdevice;

import lombok.extern.log4j.Log4j;

@Log4j
public class HcPowerDevice extends HcDeviceDriver<RawStateDto> {

	@Override
	public void output(String ipAddress, boolean enabled, boolean locked, int status, double value) {
		
		log.debug("Sending power device output "+((status == 0)?"Off":"On"));
		if (send(ipAddress, "80", "", "status="+((status == 0)?"0":"1"))) {
			log.debug("Output successful");
		} else {
			log.debug("Output failed");
		}
	}

	@Override
	public DeviceStatus statusUpdate(String reportedStatus, String ipAddress) {
		try {
			this.ipAddress = ipAddress;
			convertRaw(reportedStatus);

			if (status.getStatus() == 0) {
				log.debug("Power Off");
			} else {
				log.debug("Power On");
			}
		} catch (RawStatusException e) {
			log.debug("Could not read status update: "+reportedStatus+" for "+config.toString()+" due to "+e.getMessage());
			status.setStatusHealth(Health.BAD_DATA);
		}
		return status;
	}
}
