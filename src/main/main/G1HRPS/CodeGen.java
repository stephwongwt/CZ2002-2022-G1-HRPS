package main.G1HRPS;

import java.util.UUID;

public class CodeGen {

    /***
     * Generate a unique id using UUID.
     * Example Usage:
     *      UUID id = CodeGen.GenerateCode();
     * @return generated UUID
     */
    static public UUID GenerateCode() {
        UUID uuid = UUID.randomUUID();
		return uuid;
    }
}