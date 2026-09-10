package com.menear.mether;

import com.menear.mether.block.MetherBlocks;
import com.menear.mether.entity.MetherEntities;
import com.menear.mether.item.MetherItems;
import com.menear.mether.item.MetherFoodItems;
import com.menear.mether.item.armor.MetherArmorItems;
import com.menear.mether.item.tool.MetherToolItems;
import com.menear.mether.particle.MetherParticles;
import com.menear.mether.sound.MetherSounds;
import com.menear.mether.item.MetherSpecialItems;
import com.menear.mether.screen.MetherScreenHandlers;
import com.menear.mether.world.dimension.MetherDimension;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mether implements ModInitializer {
    public static final String MOD_ID = "mether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Mether - The Crystal Dimension Mod");
        
        MetherBlocks.initialize();
        MetherItems.initialize();
        MetherToolItems.initialize();
        MetherArmorItems.initialize();
        MetherFoodItems.initialize();
        MetherDimension.initialize();
        MetherEntities.initialize();
        MetherSounds.initialize();
        MetherParticles.initialize();
        MetherSpecialItems.initialize();
        MetherScreenHandlers.initialize();
        
        LOGGER.info("Mether mod initialized successfully! All {} registrations complete.", MOD_ID);
    }
}
