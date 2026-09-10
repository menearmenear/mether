package com.menear.mether.item;

import com.menear.mether.Mether;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MetherItems {
    
    // Crystal Shards (raw materials)
    public static final Map<CrystalType, Item> CRYSTAL_SHARDS = new HashMap<>();
    
    // Refined Crystals (processed)
    public static final Map<CrystalType, Item> REFINED_CRYSTALS = new HashMap<>();
    
    // Special Items
    public static final Item CRYSTAL_DUST = registerItem("crystal_dust",
        new Item(new Item.Settings()));
    
    public static final Item CRYSTAL_KEY = registerItem("crystal_key",
        new Item(new Item.Settings().maxCount(1)));
    
    public static final Item LUMINITE_STONE = registerItem("luminite_stone",
        new Item(new Item.Settings()));
    
    // Boss Cores
    public static final Map<CrystalType, Item> BOSS_CORES = new HashMap<>();
    
    static {
        // Register crystal shards and refined crystals
        for (CrystalType type : CrystalType.values()) {
            Item shard = registerItem(type.getName() + "_shard",
                new Item(new Item.Settings()));
            CRYSTAL_SHARDS.put(type, shard);
            
            Item refined = registerItem("refined_" + type.getName(),
                new Item(new Item.Settings()));
            REFINED_CRYSTALS.put(type, refined);
            
            // Boss cores for tier 2+ crystals
            if (type.getTier() >= 2) {
                Item core = registerItem(type.getName() + "_core",
                    new Item(new Item.Settings().maxCount(1)));
                BOSS_CORES.put(type, core);
            }
        }
    }
    
    private static Item registerItem(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(registered));
        return registered;
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering items for " + Mether.MOD_ID);
    }
}
