package com.menear.mether.world.dimension;

import com.menear.mether.Mether;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;

public class MetherDimension {
    
    public static final RegistryKey<DimensionOptions> DIMENSION_KEY = RegistryKey.of(
        RegistryKeys.DIMENSION,
        Identifier.of(Mether.MOD_ID, "mether")
    );
    
    public static final RegistryKey<World> WORLD_KEY = RegistryKey.of(
        RegistryKeys.WORLD,
        Identifier.of(Mether.MOD_ID, "mether")
    );
    
    public static final RegistryKey<DimensionType> DIMENSION_TYPE_KEY = RegistryKey.of(
        RegistryKeys.DIMENSION_TYPE,
        Identifier.of(Mether.MOD_ID, "mether")
    );
    
    public static void initialize() {
        Mether.LOGGER.info("Registering Mether dimension");
    }
}
