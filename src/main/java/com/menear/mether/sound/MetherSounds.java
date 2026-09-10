package com.menear.mether.sound;

import com.menear.mether.Mether;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MetherSounds {

    // Portal
    public static final RegistryEntry<SoundEvent> PORTAL_ACTIVATE = registerSound("portal.activate");
    public static final RegistryEntry<SoundEvent> PORTAL_AMBIENT = registerSound("portal.ambient");
    public static final RegistryEntry<SoundEvent> PORTAL_TRAVEL = registerSound("portal.travel");

    // Block
    public static final RegistryEntry<SoundEvent> CRYSTAL_BREAK = registerSound("block.crystal.break");
    public static final RegistryEntry<SoundEvent> CRYSTAL_PLACE = registerSound("block.crystal.place");
    public static final RegistryEntry<SoundEvent> CRYSTAL_STEP = registerSound("block.crystal.step");
    public static final RegistryEntry<SoundEvent> CRYSTAL_HIT = registerSound("block.crystal.hit");
    public static final RegistryEntry<SoundEvent> CRYSTAL_FALL = registerSound("block.crystal.fall");

    // Items
    public static final RegistryEntry<SoundEvent> CRYSTAL_EQUIP = registerSound("item.crystal.equip");
    public static final RegistryEntry<SoundEvent> CRYSTAL_PICKUP = registerSound("item.crystal.pickup");

    // Mobs - Crystal Golem
    public static final RegistryEntry<SoundEvent> CRYSTAL_GOLEM_AMBIENT = registerSound("mob.crystal_golem.ambient");
    public static final RegistryEntry<SoundEvent> CRYSTAL_GOLEM_HURT = registerSound("mob.crystal_golem.hurt");
    public static final RegistryEntry<SoundEvent> CRYSTAL_GOLEM_DEATH = registerSound("mob.crystal_golem.death");

    // Mobs - Crystal Spider
    public static final RegistryEntry<SoundEvent> CRYSTAL_SPIDER_AMBIENT = registerSound("mob.crystal_spider.ambient");
    public static final RegistryEntry<SoundEvent> CRYSTAL_SPIDER_HURT = registerSound("mob.crystal_spider.hurt");
    public static final RegistryEntry<SoundEvent> CRYSTAL_SPIDER_DEATH = registerSound("mob.crystal_spider.death");

    // Mobs - Shadow Wraith
    public static final RegistryEntry<SoundEvent> SHADOW_WRAITH_AMBIENT = registerSound("mob.shadow_wraith.ambient");
    public static final RegistryEntry<SoundEvent> SHADOW_WRAITH_HURT = registerSound("mob.shadow_wraith.hurt");
    public static final RegistryEntry<SoundEvent> SHADOW_WRAITH_DEATH = registerSound("mob.shadow_wraith.death");

    // Mobs - Crystal Mage
    public static final RegistryEntry<SoundEvent> CRYSTAL_MAGE_AMBIENT = registerSound("mob.crystal_mage.ambient");
    public static final RegistryEntry<SoundEvent> CRYSTAL_MAGE_HURT = registerSound("mob.crystal_mage.hurt");
    public static final RegistryEntry<SoundEvent> CRYSTAL_MAGE_DEATH = registerSound("mob.crystal_mage.death");
    public static final RegistryEntry<SoundEvent> CRYSTAL_MAGE_CAST = registerSound("mob.crystal_mage.cast");

    // Mobs - Unstable Elemental
    public static final RegistryEntry<SoundEvent> UNSTABLE_ELEMENTAL_AMBIENT = registerSound("mob.unstable_elemental.ambient");
    public static final RegistryEntry<SoundEvent> UNSTABLE_ELEMENTAL_HURT = registerSound("mob.unstable_elemental.hurt");
    public static final RegistryEntry<SoundEvent> UNSTABLE_ELEMENTAL_DEATH = registerSound("mob.unstable_elemental.death");
    public static final RegistryEntry<SoundEvent> UNSTABLE_ELEMENTAL_EXPLODE = registerSound("mob.unstable_elemental.explode");

    // Boss - Luminarch
    public static final RegistryEntry<SoundEvent> LUMINARCH_AMBIENT = registerSound("mob.luminarch.ambient");
    public static final RegistryEntry<SoundEvent> LUMINARCH_HURT = registerSound("mob.luminarch.hurt");
    public static final RegistryEntry<SoundEvent> LUMINARCH_DEATH = registerSound("mob.luminarch.death");
    public static final RegistryEntry<SoundEvent> LUMINARCH_ATTACK = registerSound("mob.luminarch.attack");
    public static final RegistryEntry<SoundEvent> LUMINARCH_ROAR = registerSound("mob.luminarch.roar");

    // Music
    public static final RegistryEntry<SoundEvent> MUSIC_CRYSTAL_PLAINS = registerSound("music.crystal_plains");
    public static final RegistryEntry<SoundEvent> MUSIC_CRYSTAL_FOREST = registerSound("music.crystal_forest");
    public static final RegistryEntry<SoundEvent> MUSIC_BOSS_LUMINARCH = registerSound("music.boss.luminarch");

    private static RegistryEntry<SoundEvent> registerSound(String name) {
        Identifier id = Identifier.of(Mether.MOD_ID, name);
        SoundEvent soundEvent = SoundEvent.of(id);
        return Registry.registerReference(Registries.SOUND_EVENT, id, soundEvent);
    }

    public static void initialize() {
        Mether.LOGGER.info("Registering sounds for " + Mether.MOD_ID);
    }
}
