package com.menear.mether.item;

import com.menear.mether.Mether;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MetherSpecialItems {
    
    public static final Item CRYSTAL_RESONATOR = registerItem("crystal_resonator",
        new Item(new Item.Settings().maxCount(1).maxDamage(64)));
    
    public static final Item CRYSTAL_COMPASS = registerItem("crystal_compass",
        new Item(new Item.Settings().maxCount(1)));
    
    public static final Item GROWTH_CATALYST = registerItem("growth_catalyst",
        new Item(new Item.Settings().maxCount(16)));
    
    public static final Item TELEPORTATION_CRYSTAL = registerItem("teleportation_crystal",
        new Item(new Item.Settings().maxCount(4)));
    
    public static final Item CRYSTAL_LENS = registerItem("crystal_lens",
        new Item(new Item.Settings().maxCount(1)));
    
    public static final Item ENERGY_CELL = registerItem("energy_cell",
        new Item(new Item.Settings().maxCount(32)));
    
    private static Item registerItem(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(registered));
        return registered;
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering special items for " + Mether.MOD_ID);
    }
}
