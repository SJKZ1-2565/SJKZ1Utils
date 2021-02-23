package com.sjkz1.sjkz1code.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stevekung.stevekungslib.utils.TextComponentUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;

public class ConfigButton extends Button
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("sjkz1code:textures/gui/config.png");
	private final Minecraft mc;
	public ConfigButton(int xPos, int yPos ,Button.IPressable button) 
	{
		super(xPos, yPos, 10, 10,  TextComponentUtils.component("C"), button);
		this.mc = Minecraft.getInstance();
	}

	

	@Override
	public void render(MatrixStack matrixStack,int mouseX, int mouseY, float partialTicks)
	{
		if (this.mc.currentScreen instanceof InventoryScreen)
		{
		
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			 this.mc.getTextureManager().bindTexture(ConfigButton.TEXTURE);
			 boolean flag = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			 AbstractGui.blit(matrixStack ,this.x, this.y, flag ? 20 : 0, 0, this.width, this.height, 20, 10);
		}
	}
}
