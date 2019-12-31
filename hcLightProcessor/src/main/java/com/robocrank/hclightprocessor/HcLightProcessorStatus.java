package com.robocrank.hclightprocessor;

import com.robocrank.hcprocessor.HcProcessorStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HcLightProcessorStatus extends HcProcessorStatus {

	public boolean light;
}
