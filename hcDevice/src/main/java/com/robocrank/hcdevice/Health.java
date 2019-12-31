package com.robocrank.hcdevice;

public enum Health {
	OK			("G"),
	BAD_DATA	("B"),
	UNKNOWN		("?"),
	ERROR		("E");
	
	private final String state;
	
	public String getStateAsString() {
		return state;
	}

	static public Health forString(String stringValue) {
		
		for (Health health : Health.values()) {
			if (health.getStateAsString().equalsIgnoreCase(stringValue)) {
				return health;
			}
		}
		
		return ERROR;
	}
	
	Health(String state) {
		this.state = state;
	}
}
