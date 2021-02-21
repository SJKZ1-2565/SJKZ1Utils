package com.sjkz1.sjkz1code.event;

import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.gui.screen.ConFigScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class SJKZ1EventHandler 
{
	public Minecraft mc;
	public static boolean dancing = false;
	public SJKZ1EventHandler() 
	{
		this.mc = Minecraft.getInstance();
	}
	@SubscribeEvent
	public void onAttackEntity(LivingAttackEvent e)
	{
		if(SJKZ1CodeSettings.INSTANCE.playSoundWhenProjectileHitTheTarget)
		{
			if(e.getSource().isProjectile() && e.getSource().getTrueSource() instanceof PlayerEntity)
			{
				mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, -1F);
			}
		}
	}
	@SubscribeEvent
	public void removeFireOverlay(RenderBlockOverlayEvent event)
	{
		if(SJKZ1CodeSettings.INSTANCE.disableFireOverlay)
		{
			if(event.getOverlayType() == OverlayType.FIRE)
			{
				event.setCanceled(SJKZ1CodeSettings.INSTANCE.disableFireOverlay);
			}
		}
	}
	@SubscribeEvent
	public void onPressKey(InputEvent.KeyInputEvent event)
	{
		if (SJKZ1KeyBinding.OPEN_CONFIG.isKeyDown())
		{
			this.mc.displayGuiScreen(new ConFigScreen());
		}
		else if(SJKZ1KeyBinding.DANCE.isKeyDown())
		{
			dancing = !dancing;
		}
	}
	@SubscribeEvent
	public void Dancing(TickEvent.ClientTickEvent event)
	{
		if(event.phase == TickEvent.Phase.START)
		{
			if(dancing == true)
			{
				if(mc.player.ticksExisted %10 == 0)
				{
					mc.player.swingArm(Hand.MAIN_HAND);
				}
				if(mc.player.ticksExisted %10 == 5)
				{
					mc.player.swingArm(Hand.OFF_HAND);
				}
			}
		}
	}

}