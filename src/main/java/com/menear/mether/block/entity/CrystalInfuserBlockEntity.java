package com.menear.mether.block.entity;

import com.menear.mether.Mether;
import com.menear.mether.block.MetherBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentType;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CrystalInfuserBlockEntity extends LockableContainerBlockEntity {

    public static final ComponentType<Integer> INFUSION_PROGRESS = Registry.register(
        Registries.DATA_COMPONENT_TYPE,
        Identifier.of(Mether.MOD_ID, "infusion_progress"),
        ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    private int infusionProgress = 0;

    public CrystalInfuserBlockEntity(BlockPos pos, BlockState state) {
        super(MetherBlocks.CRYSTAL_INFUSER_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.mether.crystal_infuser");
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> list) {
        inventory = list;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int size() {
        return 6;
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        this.infusionProgress = components.getOrDefault(INFUSION_PROGRESS, 0);
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(INFUSION_PROGRESS, this.infusionProgress);
    }

    public int getInfusionProgress() {
        return infusionProgress;
    }

    public void setInfusionProgress(int progress) {
        this.infusionProgress = progress;
    }
}