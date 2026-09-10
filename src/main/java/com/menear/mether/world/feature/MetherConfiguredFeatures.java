package com.menear.mether.world.feature;

import com.menear.mether.Mether;
import com.menear.mether.block.MetherBlocks;
import com.menear.mether.util.CrystalType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class MetherConfiguredFeatures {
    
    public static final RegistryKey<ConfiguredFeature<?, ?>> LUMINITE_ORE_KEY = registerKey("luminite_ore");
    
    private static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Mether.MOD_ID, name));
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering configured features for " + Mether.MOD_ID);
    }
}
