package com.yak.xebia;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
@Component
public class ServiceProperties {

	private int daysInYear ;
	private int minimumAgeToShave;
	private int maxAgeOfYak;

	public int getDaysInYear() {
		return daysInYear;
	}

	public void setDaysInYear(int daysInYear) {
		this.daysInYear = daysInYear;
	}

	public int getMinimumAgeToShave() {
		return minimumAgeToShave;
	}

	public void setMinimumAgeToShave(int minimumAgeToShave) {
		this.minimumAgeToShave = minimumAgeToShave;
	}

	public int getMaxAgeOfYak() {
		return maxAgeOfYak;
	}

	public void setMaxAgeOfYak(int maxAgeOfYak) {
		this.maxAgeOfYak = maxAgeOfYak;
	}

}
