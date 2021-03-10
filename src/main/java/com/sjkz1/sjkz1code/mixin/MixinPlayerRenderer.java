package com.sjkz1.sjkz1code.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sjkz1.sjkz1code.renderer.GlowingSkin;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.PlayerRenderer;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer 
{

	private final PlayerRenderer that = (PlayerRenderer) (Object) this;
	
	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererManager;Z)V", at = @At("RETURN"))
	private void init(EntityRendererManager renderManager, boolean useSmallArms, CallbackInfo info)
	{
		this.that.addLayer(new GlowingSkin(this.that));
	}
}

