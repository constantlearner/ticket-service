package com.walmart.ticketbooking.util;

import static com.walmart.ticketbooking.consts.ServiceConsts.CODE_DELIMITER;
import static com.walmart.ticketbooking.consts.ServiceConsts.CODE_GENERATOR_SEED_PREFIX;

import java.util.UUID;

public class ConfirmationCodeUtils {

	private ConfirmationCodeUtils() {}

	public static String generateCode(int seatHoldId, String customerEmail) {
		String name = String.join(CODE_DELIMITER, CODE_GENERATOR_SEED_PREFIX, customerEmail, String.valueOf(seatHoldId));
		UUID uuid = UUID.nameUUIDFromBytes(name.getBytes());
		return uuid.toString();
	}
}
