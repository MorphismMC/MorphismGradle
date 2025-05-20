package com.example.examplemod;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = ExampleMod.ID, dist = Dist.CLIENT)
public final class ExampleModClient extends ExampleModCommon {

    public ExampleModClient(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
    }
}
