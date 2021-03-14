package com.sjkz1.sjkz1code.event;

import java.util.List;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.gui.screen.ConfigScreen;
import com.sjkz1.sjkz1code.gui.toasts.FaceBookToasts;
import com.sjkz1.sjkz1code.gui.toasts.LoginToasts;
import com.sjkz1.sjkz1code.gui.toasts.YoutubeToasts;
import com.sjkz1.sjkz1code.utils.InfoUtils;
import com.stevekung.stevekungslib.utils.GameProfileUtils;
import com.stevekung.stevekungslib.utils.TextComponentUtils;
import com.stevekung.stevekungslib.utils.client.ClientUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggedInEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class SJKZ1EventHandler 
{
	private final Minecraft mc;
	public static boolean dancing = false;
	public static boolean glow = true;

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
		if(event.getOverlayType() == OverlayType.WATER && event.getOverlayType() == OverlayType.FIRE)
			{
				event.setCanceled(SJKZ1CodeSettings.INSTANCE.disableOverlays);
			}
	}
	
	@SubscribeEvent
	public void onPlayerLoggin(LoggedInEvent event)
	{
		if(event.getPlayer() != null)
		{
			String playerName = GameProfileUtils.getUsername();
			if(SJKZ1CodeSettings.INSTANCE.loginToast)
			{
				this.mc.getToastGui().add(new LoginToasts(playerName));
				this.mc.getToastGui().add(new YoutubeToasts("Youtube : SJKZ1"));
				this.mc.getToastGui().add(new FaceBookToasts("Facebook"));
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
		else if(SJKZ1KeyBinding.GLOWING_LAYER.isKeyDown())
		{
			glow = !glow;
		}
	}
	
	@SubscribeEvent
	public void TickEvent(TickEvent.ClientTickEvent event)
	{

		if(event.phase == TickEvent.Phase.START)
		{
			InfoUtils.INSTANCE.getMouseOverEntityExtended(this.mc);
			if(mc.player != null && mc.world != null)
			{
				CreeperEntity creeper  = getNearestCreeperEntity(mc.player);
				if(creeper != null && SJKZ1CodeSettings.INSTANCE.CreeperDetector && (!mc.player.isCreative() && !mc.player.isSpectator()))
				{
					if(mc.player.ticksExisted %20 == 0)
					{
						int distance = (int) mc.player.getDistance(creeper);
						ClientUtils.setOverlayMessage(TextFormatting.DARK_GREEN + "Creeper" +TextFormatting.DARK_RED + " is nearby you! \u2248 " + String.valueOf(distance) + " block away!");
					}
				}
				else if(dancing == true)
				{
				
					if(mc.player.ticksExisted %10 == 0)
					{
						mc.player.swingArm(Hand.MAIN_HAND);
						mc.player.processInitialInteract(mc.player, Hand.MAIN_HAND);
					}
					if(mc.player.ticksExisted %10 == 5)
					{
						mc.player.swingArm(Hand.OFF_HAND);
					}
				}
				else if(mc.player.isElytraFlying() && SJKZ1CodeSettings.INSTANCE.AutoElytraBoost)
				{
					if(mc.player.getHeldItemMainhand().getItem() instanceof FireworkRocketItem || mc.player.getHeldItemOffhand().getItem() instanceof FireworkRocketItem)
					{
						Hand hand = mc.player.getActiveHand();
						
						if(mc.player.ticksExisted %120 == 0)
						{
							mc.playerController.processRightClick(mc.player, mc.world, hand);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void AutoRespawn(LivingDeathEvent event) 
	{
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
		if(screen instanceof MainMenuScreen)
		{
			MainMenuScreen menu = (MainMenuScreen) event.getGui();
			if(InfoUtils.isMookBirhtDay())
			{
				menu.splashText = "Happy birthday, Mook \u2764";
			}
		}

	}
	
	@SubscribeEvent
	public void onInitGui(RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event)
	{
		LivingEntity entity = event.getEntity();
		float health = entity.getHealth();
		MatrixStack stack = event.getMatrixStack();
		IRenderTypeBuffer Buffer = event.getBuffers();
		boolean halfHealth = health <= entity.getMaxHealth() / 2F;
		boolean halfHealth1 = health <= entity.getMaxHealth() / 4F;
		double distance = entity.getDistanceSq(this.mc.getRenderManager().info.getProjectedView());
		TextFormatting color = halfHealth ? TextFormatting.RED : halfHealth1 ? TextFormatting.DARK_RED : TextFormatting.GREEN;
		if (distance < 1024.0D)
		{
			if (SJKZ1CodeSettings.INSTANCE.HealtStatus && !this.mc.gameSettings.hideGUI && !entity.isInvisible() && !(entity instanceof ClientPlayerEntity || entity instanceof ArmorStandEntity) && entity == InfoUtils.INSTANCE.extendedPointedEntity)
			{
				ITextComponent displayNameInheart = TextComponentUtils.formatted("\u2764 " +  String.format("%.1f", health),color);
				float height = entity.getHeight() + 0.5F;
				stack.push();
				stack.translate(0.0D, height, 0.0D);
				stack.rotate(this.mc.getRenderManager().getCameraOrientation());
				stack.scale(-0.025F, -0.025F, 0.025F);
				Matrix4f matrix4f = stack.getLast().getMatrix();
				float textBackgroundOpacity = mc.gameSettings.getTextBackgroundOpacity(0.25F);
				int textColor = (int)(textBackgroundOpacity * 255.0F) << 24;
				FontRenderer fontrenderer = this.mc.getRenderManager().getFontRenderer();
				float textX = -fontrenderer.getStringPropertyWidth(displayNameInheart) / 2;
				fontrenderer.func_243247_a(displayNameInheart, textX, -15, -1, false, matrix4f, Buffer, false, textColor, 155);
				stack.pop();
			}
		}
	}

	@Nullable
	private CreeperEntity getNearestCreeperEntity(PlayerEntity player)
	{
		Vector3d lookVec = player.getLookVec().normalize();
		Vector3d targetPos = player.getPositionVec().add(lookVec.x, 1, lookVec.z);
		List<CreeperEntity> creeper = player.world.getEntitiesWithinAABB(CreeperEntity.class, new AxisAlignedBB(targetPos.subtract(10, 10, 10), targetPos.add(10, 10, 10)));
		if(creeper.size() > 0)
		{
			float closestDistance = 8F;
			CreeperEntity entity = null;
			for(CreeperEntity CreeperEntities : creeper)
			{
				float distance = player.getDistance(CreeperEntities);
				if(distance < closestDistance || closestDistance == 8F)
				{
					closestDistance = distance;
					entity = CreeperEntities;
				}
			}
			return entity;
		}
		return null;
	}
}