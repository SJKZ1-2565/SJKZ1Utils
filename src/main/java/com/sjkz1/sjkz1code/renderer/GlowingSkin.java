package com.sjkz1.sjkz1code.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sjkz1.sjkz1code.event.SJKZ1EventHandler;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GlowingSkin extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("sjkz1code:textures/entity/sjkz1.png");

    public GlowingSkin(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> renderer)
    {
        super(renderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (!entity.isInvisible() && entity.getName().getString().equals("SJKZ1") && SJKZ1EventHandler.glow == true)
        {
            float time = entity.ticksExisted + partialTicks;
            float alpha = (MathHelper.sin(time / 24) + 1F) / 2F + 0.15F;

            if (alpha > 0.7F)
            {
                alpha = 0.7F;
            }
            IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderStuff.getGlowingOverlay(GlowingSkin.TEXTURE));
            this.getEntityModel().render(matrixStack, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, alpha, alpha, alpha, 1.0F);
        }
    }
}
