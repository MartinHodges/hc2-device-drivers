package com.robocrank.hcdaytimeprocessor;

import com.robocrank.hcprocessor.HcProcessorStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HcDayTimeProcessorStatus extends HcProcessorStatus{

	public boolean daytime;
}
