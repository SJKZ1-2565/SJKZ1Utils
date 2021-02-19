package com.sjkz1.sjkz1code.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;


public class SJKZ1EventHandler 
{
	public Minecraft mc;
	public PlayerEntity player;
	public SJKZ1EventHandler() 
	{
		this.mc = Minecraft.getInstance();
	}
	@SubscribeEvent
	public void tes(LivingAttackEvent e) 
	{
		if(e.getSource().setProjectile().getTrueSource() instanceof PlayerEntity && !mc.player.abilities.isCreativeMode)
		{
			mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, -1F);
		}
	}
}

