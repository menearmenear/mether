package com.menear.mether.particle;

import com.menear.mether.Mether;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MetherParticles {
    
    public static final SimpleParticleType CRYSTAL_SPARKLE = register("crystal_sparkle");
    public static final SimpleParticleType CRYSTAL_AMBIENT = register("crystal_ambient");
    public static final SimpleParticleType PORTAL_CRYSTAL = register("portal_crystal");
    public static final SimpleParticleType BOSS_DEATH = register("boss_death");
    public static final SimpleParticleType CRYSTAL_GROW = register("crystal_grow");
    
    private static SimpleParticleType register(String name) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Mether.MOD_ID, name),
            FabricParticleTypes.simple());
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering particles for " + Mether.MOD_ID);
    }
}
