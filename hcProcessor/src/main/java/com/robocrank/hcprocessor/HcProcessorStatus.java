package com.robocrank.hcprocessor;

import com.robocrank.hcdevice.Health;

import lombok.Data;

@Data
public class HcProcessorStatus {

	Health health = Health.UNKNOWN;
}
