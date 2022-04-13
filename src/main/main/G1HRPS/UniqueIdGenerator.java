package main.G1HRPS;

import java.util.UUID;

public class UniqueIdGenerator {

    /***
     * Generate a unique id using UUID.
     * Example Usage:
     *      UUID id = UniqueIdGenerator.Generate();
     * @return generated UUID
     */
    static public UUID Generate() {
        UUID uuid = UUID.randomUUID();
		return uuid;
    }
}