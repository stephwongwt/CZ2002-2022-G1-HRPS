package main.G1HRPS;

import java.util.UUID;

/**
 * Utility to generate unique id.
 * 
 * @author Steph Wong
 *
 */
public class UniqueIdGenerator {

	/***
	 * Generate a unique id using UUID. Example Usage: UUID id =
	 * UniqueIdGenerator.Generate();
	 * 
	 * @return generated UUID
	 */
	static public UUID Generate() {
		UUID uuid = UUID.randomUUID();
		return uuid;
	}
}