package com.sjkz1.sjkz1code.event;

import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.gui.button.ConfigButton;
import com.sjkz1.sjkz1code.gui.screen.ConfigScreen;
import com.sjkz1.sjkz1code.gui.toasts.LoginToasts;
import com.stevekung.stevekungslib.utils.GameProfileUtils;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class SJKZ1EventHandler 
{
	private final Minecraft mc;
	public static boolean dancing = false;
	public static boolean show = false;
	public SJKZ1EventHandler() 
	{
		this.mc = Minecraft.getInstance();
	}
	@SubscribeEvent
	public void onAttackEntity(LivingAttackEvent e)
	{
		if(SJKZ1CodeSettings.INSTANCE.playSoundWhenProjectileHitTheEntity)
		{
			if(e.getSource().isProjectile() && e.getSource().getTrueSource() instanceof PlayerEntity)
			{
				mc.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.4F, -1F);
			}
		}
	}
	@SubscribeEvent
	public void disableFireOverlay(RenderBlockOverlayEvent event)
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
	public void onPlayerLoggin(LoggedInEvent event)
	{
		if(event.getPlayer() != null)
		{
			String toasts = GameProfileUtils.getUsername();
			if(SJKZ1CodeSettings.INSTANCE.loginToast)
			{
				this.mc.getToastGui().add(new LoginToasts(toasts));
			}
		}
	}
	@SubscribeEvent
	public void onPressKey(InputEvent.KeyInputEvent event)
	{
		if (SJKZ1KeyBinding.OPEN_CONFIG.isKeyDown())
		{
			this.mc.displayGuiScreen(new ConfigScreen());
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
	
	@SubscribeEvent
	public void AutoRespawn(LivingDeathEvent event) {
		 
		
			if (event.getEntity() instanceof PlayerEntity && SJKZ1CodeSettings.INSTANCE.autoRespawn) 
			{
				mc.player.respawnPlayer();
				ClientUtils.printClientMessage("Dead Position X: " + mc.player.getPosition().getX() + " Y: " + mc.player.getPosition().getY() + " Z: " + mc.player.getPosition().getZ(),TextFormatting.YELLOW);
			}
		
	}
	
	@SubscribeEvent
	public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event)
	{
		Screen screen = event.getGui();
			if (screen instanceof InventoryScreen)
			{
				int height = screen.height  / 2 - 106;
				int width = screen.width / 2;
				event.addWidget(new ConfigButton(width + 37, height + 90, button -> this.mc.displayGuiScreen(new ConfigScreen())));
			}	
	}
}