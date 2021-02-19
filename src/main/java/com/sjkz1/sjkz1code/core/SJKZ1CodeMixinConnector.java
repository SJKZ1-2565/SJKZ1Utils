package com.sjkz1.sjkz1code.core;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class SJKZ1CodeMixinConnector implements IMixinConnector
{
    @Override
    public void connect()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixin.sjkz1code.json");
    }
}