package com.menear.mether.item;

import com.menear.mether.Mether;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MetherFoodItems {
    
    public static final Item CRYSTAL_BERRIES = registerFood("crystal_berries",
        new Item(new Item.Settings().food(
            new net.minecraft.item.FoodComponent.Builder()
                .hunger(3)
                .saturationModifier(0.6f)
                .effect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0), 1.0f)
                .snack()
                .build()
        )));
    
    public static final Item GLOWING_MUSHROOM = registerFood("glowing_mushroom",
        new Item(new Item.Settings().food(
            new net.minecraft.item.FoodComponent.Builder()
                .hunger(2)
                .saturationModifier(0.4f)
                .effect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0), 1.0f)
                .snack()
                .build()
        )));
    
    public static final Item CRYSTAL_HONEY = registerFood("crystal_honey",
        new Item(new Item.Settings().food(
            new net.minecraft.item.FoodComponent.Builder()
                .hunger(4)
                .saturationModifier(0.8f)
                .effect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 1.0f)
                .build()
        )));
    
    public static final Item CRYSTAL_APPLE = registerFood("crystal_apple",
        new Item(new Item.Settings().food(
            new net.minecraft.item.FoodComponent.Builder()
                .hunger(6)
                .saturationModifier(1.2f)
                .effect(new StatusEffectInstance(StatusEffects.ABSORPTION, 600, 0), 1.0f)
                .effect(new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0), 0.5f)
                .build()
        )));
    
    public static final Item LUMINITE_FRUIT = registerFood("luminite_fruit",
        new Item(new Item.Settings().food(
            new net.minecraft.item.FoodComponent.Builder()
                .hunger(8)
                .saturationModifier(1.5f)
                .effect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1.0f)
                .effect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 0), 1.0f)
                .alwaysEdible()
                .build()
        )));
    
    private static Item registerFood(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(registered));
        return registered;
    }
    
    public static void initialize() {
        Mether.LOGGER.info("Registering food items for " + Mether.MOD_ID);
    }
}
