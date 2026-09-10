package com.menear.mether.entity;

import com.menear.mether.Mether;
import com.menear.mether.entity.mob.*;
import com.menear.mether.entity.boss.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class MetherEntities {
    
    // Passive Mobs
    public static final EntityType<CrystalButterflyEntity> CRYSTAL_BUTTERFLY = register(
        "crystal_butterfly",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrystalButterflyEntity::new)
            .dimensions(EntityType.CREEPER.getDimensions())
            .build(key("crystal_butterfly"))
    );
    
    public static final EntityType<LuminaSheepEntity> LUMINA_SHEEP = register(
        "lumina_sheep",
        FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LuminaSheepEntity::new)
            .dimensions(EntityType.SHEEP.getDimensions())
            .build(key("lumina_sheep"))
    );
    
    // Hostile Mobs
    public static final EntityType<CrystalGolemEntity> CRYSTAL_GOLEM = register(
        "crystal_golem",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CrystalGolemEntity::new)
            .dimensions(EntityType.IRON_GOLEM.getDimensions())
            .build(key("crystal_golem"))
    );
    
    public static final EntityType<CrystalSpiderEntity> CRYSTAL_SPIDER = register(
        "crystal_spider",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CrystalSpiderEntity::new)
            .dimensions(EntityType.SPIDER.getDimensions())
            .build(key("crystal_spider"))
    );
    
    public static final EntityType<ShadowWraithEntity> SHADOW_WRAITH = register(
        "shadow_wraith",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShadowWraithEntity::new)
            .dimensions(EntityType.VEX.getDimensions())
            .build(key("shadow_wraith"))
    );
    
    public static final EntityType<CrystalMageEntity> CRYSTAL_MAGE = register(
        "crystal_mage",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CrystalMageEntity::new)
            .dimensions(EntityType.VINDICATOR.getDimensions())
            .build(key("crystal_mage"))
    );
    
    public static final EntityType<UnstableElementalEntity> UNSTABLE_ELEMENTAL = register(
        "unstable_elemental",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, UnstableElementalEntity::new)
            .dimensions(EntityType.BLAZE.getDimensions())
            .build(key("unstable_elemental"))
    );
    
    // Boss Mobs
    public static final EntityType<LuminarchEntity> LUMINARCH = register(
        "luminarch",
        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, LuminarchEntity::new)
            .dimensions(EntityType.ELDER_GUARDIAN.getDimensions())
            .trackRangeChunks(50)
            .build(key("luminarch"))
    );
    
    private static RegistryKey<EntityType<?>> key(String name) {
        return RegistryKey.of(Registries.ENTITY_TYPE.getKey(), Identifier.of(Mether.MOD_ID, name));
    }
    
    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Mether.MOD_ID, name), type);
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering entities for " + Mether.MOD_ID);
    }
}
