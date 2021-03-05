package com.sjkz1.sjkz1code.gui.toasts;
import java.util.Date;

import com.ibm.icu.util.Calendar;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sjkz1.sjkz1code.core.SJKZ1CodeMod;
import com.stevekung.stevekungslib.utils.ColorUtils;
import com.stevekung.stevekungslib.utils.ItemUtils;
import com.stevekung.stevekungslib.utils.TextComponentUtils;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class LoginToasts  implements IToast
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("sjkz1code:textures/gui/login_toasts.png");
    private final ItemStack itemStack;
    private final String name;

    public LoginToasts(String name)
    {
    	this.itemStack = ItemUtils.getPlayerHead(name);
        this.name = name;
    }

    @SuppressWarnings({ "deprecation", "resource" })
    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long delta)
    {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        AbstractGui.blit(matrixStack, 0, 0, 0, 0, 160, 32, 160, 32);
        toastGui.getMinecraft().fontRenderer.func_243246_a(matrixStack, TextComponentUtils.formatted(this.name, TextFormatting.BOLD), 30, 7, ColorUtils.toDecimal(255, 255, 85));
        toastGui.getMinecraft().fontRenderer.drawString(matrixStack, SJKZ1CodeMod.MOD_ID.toUpperCase() + ": " + SJKZ1CodeMod.VERSION , 30, 18, ColorUtils.toDecimal(255, 255, 255));
        toastGui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(this.itemStack, 8, 8);
        return delta >= 15000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }
}
