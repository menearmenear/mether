package com.menear.mether;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mether implements ModInitializer {
    public static final String MOD_ID = "mether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Mether - The Dimension Mod");
        LOGGER.info("Preparing to enter the Mether dimension...");
    }
}
