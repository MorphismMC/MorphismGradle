package com.example.examplemod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = ExampleMod.ID, dist = Dist.DEDICATED_SERVER)
public final class ExampleModServer extends ExampleModCommon {

    public ExampleModServer(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
    }
}
