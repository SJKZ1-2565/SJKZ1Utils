package com.sjkz1.sjkz1code.renderer;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
public class RenderStuff extends RenderState
{
    private RenderStuff(String name, Runnable setupTask, Runnable clearTask)
    {
        super(name, setupTask, clearTask);
    }

    public static RenderType getGlowingOverlay(ResourceLocation location)
    {
        RenderState.TextureState textureState = new RenderState.TextureState(location, false, false);
        return RenderType.makeType("glowing_overlay", DefaultVertexFormats.ENTITY, 7, 256, false, true, RenderType.State.getBuilder().texture(textureState).transparency(GLINT_TRANSPARENCY).writeMask(COLOR_WRITE).fog(BLACK_FOG).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).build(false));
    }
}
