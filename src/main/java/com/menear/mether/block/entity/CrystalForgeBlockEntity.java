package com.menear.mether.block.entity;

import com.menear.mether.block.MetherBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CrystalForgeBlockEntity extends LockableContainerBlockEntity {
    
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    
    public CrystalForgeBlockEntity(BlockPos pos, BlockState state) {
        super(MetherBlocks.CRYSTAL_FORGE_BLOCK_ENTITY, pos, state);
    }
    
    @Override
    public Text getDisplayName() {
        return Text.translatable("container.mether.crystal_forge");
    }
    
    @Override
    protected Text getContainerName() {
        return Text.translatable("container.mether.crystal_forge");
    }
    
    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return null;
    }
    
    @Override
    public int size() {
        return 9;
    }
    
    @Override
    public boolean isEmpty() {
        return inventory.stream().allMatch(ItemStack::isEmpty);
    }
    
    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }
    
    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }
    
    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }
    
    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCount(stack)) {
            stack.setCount(getMaxCount(stack));
        }
    }
    
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canUse(player, MetherBlocks.CRYSTAL_FORGE, player.getBlockPos());
    }
    
    @Override
    public void clear() {
        inventory.clear();
    }
    
    @Override
    protected void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }
    
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }
}
