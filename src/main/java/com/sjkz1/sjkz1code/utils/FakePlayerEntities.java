package com.sjkz1.sjkz1code.utils;

import com.mojang.authlib.GameProfile;
import com.stevekung.stevekungslib.client.event.handler.ClientEventHandler;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public class FakePlayerEntities extends AbstractClientPlayerEntity
{
    public FakePlayerEntities(ClientWorld world, GameProfile profile)
    {
        super(world, profile);
    }

    @Override
    public void tick()
    {
        this.ticksExisted = ClientEventHandler.ticks;
    }
}