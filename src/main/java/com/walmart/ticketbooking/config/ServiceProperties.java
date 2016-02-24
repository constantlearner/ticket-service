package com.walmart.ticketbooking.config;

/**
 * Class for having global variables for service
 * 
 * @author sandeepda
 *
 */
public final class ServiceProperties {
	private final Long seatHoldExpireTime;	// default

	public ServiceProperties(Long seatHoldExpireTime) {
		this.seatHoldExpireTime = seatHoldExpireTime;
	}

	public final Long getSeatHoldExpireTime() {
		return seatHoldExpireTime;
	}

}
