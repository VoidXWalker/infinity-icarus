package com.voidxwalker.infinityicarus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    private static final ModContainer mod = (ModContainer) FabricLoader.getInstance().getModContainer("infinityicarus").orElseThrow(NullPointerException::new);


    public void onInitialize() {
    }

}
