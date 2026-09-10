package com.menear.mether.screen;

import com.menear.mether.Mether;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class MetherScreenHandlers {

    public static final ScreenHandlerType<CrystalForgeScreenHandler> CRYSTAL_FORGE = Registry.register(
        Registries.SCREEN_HANDLER,
        Identifier.of(Mether.MOD_ID, "crystal_forge"),
        new ScreenHandlerType<>(CrystalForgeScreenHandler::new)
    );

    public static final ScreenHandlerType<CrystalInfuserScreenHandler> CRYSTAL_INFUSER = Registry.register(
        Registries.SCREEN_HANDLER,
        Identifier.of(Mether.MOD_ID, "crystal_infuser"),
        new ScreenHandlerType<>(CrystalInfuserScreenHandler::new)
    );

    public static void initialize() {
        Mether.LOGGER.info("Registering screen handlers for " + Mether.MOD_ID);
    }
}
