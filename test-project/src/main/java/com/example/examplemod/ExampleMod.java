package com.example.examplemod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ExampleMod {

    // Define mod id in a common place for everything to reference
    String ID = "examplemod";
    String NAME = "Example Mod";
    // Directly reference a slf4j logger
    Logger LOGGER = LoggerFactory.getLogger(NAME);
}
