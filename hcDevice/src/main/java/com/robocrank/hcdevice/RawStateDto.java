package com.robocrank.hcdevice;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RawStateDto {

	public Health statusHealth = Health.UNKNOWN;;

	public int powerHealth = 0;

}
