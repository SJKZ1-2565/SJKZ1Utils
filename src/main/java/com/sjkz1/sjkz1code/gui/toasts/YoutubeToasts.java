package com.sjkz1.sjkz1code.gui.toasts;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stevekung.stevekungslib.utils.ColorUtils;
import com.stevekung.stevekungslib.utils.TextComponentUtils;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class YoutubeToasts  implements IToast
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("sjkz1code:textures/gui/youtube_toasts.png");
    private final String name;

    public YoutubeToasts(String name)
    {
        this.name = name;
    }

    @SuppressWarnings({ "deprecation", "resource" })
    @Override
    public IToast.Visibility func_230444_a_(MatrixStack matrixStack, ToastGui toastGui, long delta)
    {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        AbstractGui.blit(matrixStack, 0, 0, 0, 0, 160, 32, 160, 32);
        toastGui.getMinecraft().fontRenderer.func_243246_a(matrixStack, TextComponentUtils.formatted(this.name, TextFormatting.BOLD), 30, 7, ColorUtils.toDecimal(255, 0, 0));
        toastGui.getMinecraft().fontRenderer.drawString(matrixStack, "Please Subscribe!" , 30, 18, ColorUtils.toDecimal(10, 160, 165));
        return delta >= 15000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
    }
}
