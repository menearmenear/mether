package com.menear.mether;

import com.menear.mether.block.MetherBlocks;
import com.menear.mether.item.MetherItems;
import com.menear.mether.item.armor.MetherArmorItems;
import com.menear.mether.item.tool.MetherToolItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mether implements ModInitializer {
    public static final String MOD_ID = "mether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Mether - The Crystal Dimension Mod");
        
        // Register blocks, items, tools, and armor
        MetherBlocks.initialize();
        MetherItems.initialize();
        MetherToolItems.initialize();
        MetherArmorItems.initialize();
        
        LOGGER.info("Mether mod initialized successfully!");
    }
}
