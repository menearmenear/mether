package com.menear.mether.item.tool;

import com.menear.mether.Mether;
import com.menear.mether.util.CrystalType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MetherToolItems {

    public static final TagKey<Item> REPAIRS_CRYSTAL_TOOLS =
        TagKey.of(RegistryKeys.ITEM, Identifier.of(Mether.MOD_ID, "repairs_crystal_tools"));

    // Tool sets for each crystal type
    public static final Map<CrystalType, ToolSet> TOOL_SETS = new HashMap<>();

    public static class ToolSet {
        public final Item pickaxe;
        public final Item axe;
        public final Item shovel;
        public final Item hoe;
        public final Item sword;

        public ToolSet(Item pickaxe, Item axe, Item shovel, Item hoe, Item sword) {
            this.pickaxe = pickaxe;
            this.axe = axe;
            this.shovel = shovel;
            this.hoe = hoe;
            this.sword = sword;
        }
    }

    static {
        for (CrystalType type : CrystalType.values()) {
            ToolMaterial material = createToolMaterial(type);

            Item pickaxe = registerTool(type.getName() + "_pickaxe",
                new Item(new Item.Settings().pickaxe(material, 1.0f, -2.8f)));

            Item axe = registerTool(type.getName() + "_axe",
                new AxeItem(material, 6.0f, -3.0f, new Item.Settings()));

            Item shovel = registerTool(type.getName() + "_shovel",
                new ShovelItem(material, 1.5f, -3.0f, new Item.Settings()));

            Item hoe = registerTool(type.getName() + "_hoe",
                new HoeItem(material, 0.0f, -3.0f, new Item.Settings()));

            Item sword = registerTool(type.getName() + "_sword",
                new Item(new Item.Settings().sword(material, 3.0f, -2.4f)));

            TOOL_SETS.put(type, new ToolSet(pickaxe, axe, shovel, hoe, sword));
        }
    }

    private static ToolMaterial createToolMaterial(CrystalType type) {
        TagKey<Block> incorrectForDrops = switch (type.getTier()) {
            case 1 -> BlockTags.INCORRECT_FOR_IRON_TOOL;
            case 2 -> BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
            case 3, 4 -> BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            default -> BlockTags.INCORRECT_FOR_STONE_TOOL;
        };

        int durability = switch (type.getTier()) {
            case 1 -> 500;   // Luminite - Iron level
            case 2 -> 1561;  // Elemental - Diamond level
            case 3 -> 2500;  // Advanced - Better than diamond
            case 4 -> 3500;  // Prismarite - Better than netherite
            default -> 250;
        };

        float speed = switch (type.getTier()) {
            case 1 -> 6.0f;
            case 2 -> 8.0f;
            case 3 -> 10.0f;
            case 4 -> 12.0f;
            default -> 4.0f;
        };

        float attackDamage = switch (type.getTier()) {
            case 1 -> 2.0f;
            case 2 -> 3.0f;
            case 3 -> 4.0f;
            case 4 -> 5.0f;
            default -> 1.0f;
        };

        int enchantability = 10 + (type.getTier() * 5);

        return new ToolMaterial(
            incorrectForDrops,
            durability,
            speed,
            attackDamage,
            enchantability,
            REPAIRS_CRYSTAL_TOOLS
        );
    }

    private static Item registerTool(String name, Item item) {
        Item registered = Registry.register(Registries.ITEM, Identifier.of(Mether.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(registered));
        return registered;
    }

    public static void initialize() {
        Mether.LOGGER.info("Registering tools for " + Mether.MOD_ID);
    }
}