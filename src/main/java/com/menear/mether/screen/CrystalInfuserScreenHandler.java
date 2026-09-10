package com.menear.mether.screen;

import com.menear.mether.block.MetherBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class CrystalInfuserScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    public CrystalInfuserScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6));
    }

    public CrystalInfuserScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(MetherScreenHandlers.CRYSTAL_INFUSER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;

        // Crystal Infuser inventory (2x3 grid)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 2; col++) {
                this.addSlot(new Slot(inventory, col + row * 2, 62 + col * 18, 17 + row * 18));
            }
        }

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack result = ItemStack.EMPTY;
        Slot slotInv = this.slots.get(slot);
        if (slotInv != null && slotInv.hasStack()) {
            ItemStack stack = slotInv.getStack();
            result = stack.copy();
            if (slot < this.inventory.size()) {
                if (!this.insertItem(stack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slotInv.setStack(ItemStack.EMPTY);
            } else {
                slotInv.markDirty();
            }
        }
        return result;
    }
}
