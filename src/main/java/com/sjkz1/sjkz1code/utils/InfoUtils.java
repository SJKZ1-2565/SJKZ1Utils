package com.sjkz1.sjkz1code.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class InfoUtils 
{
	public static final InfoUtils INSTANCE = new InfoUtils();
	public Entity extendedPointedEntity;
	public static boolean isMookBirhtDay()
	{
		return month() == 6 && day() == 19;
	}
	public static int month()
	{
		return LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
	}

	public static int day()
	{
		return LocalDate.now().get(ChronoField.DAY_OF_MONTH);
	}
	public void getMouseOverEntityExtended(Minecraft mc)
    {
        Entity entity = mc.getRenderViewEntity();
        double distance = 12.0D;

        if (entity != null)
        {
            this.extendedPointedEntity = null;
            mc.objectMouseOver = entity.pick(distance, mc.getRenderPartialTicks(), false);
            Vector3d eyePos = entity.getEyePosition(mc.getRenderPartialTicks());
            distance *= distance;

            if (mc.objectMouseOver != null)
            {
                distance = mc.objectMouseOver.getHitVec().squareDistanceTo(eyePos);
            }

            Vector3d vecLook = entity.getLook(1.0F);
            Vector3d vec3d2 = eyePos.add(vecLook.x * distance, vecLook.y * distance, vecLook.z * distance);
            AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vecLook.scale(distance)).expand(1.0D, 1.0D, 1.0D);
            EntityRayTraceResult result = ProjectileHelper.rayTraceEntities(entity, eyePos, vec3d2, axisalignedbb, entityFilter -> !entityFilter.isSpectator() && entityFilter.canBeCollidedWith(), distance);

            if (result != null)
            {
                Entity pointedEntity = result.getEntity();
                Vector3d hitVec = result.getHitVec();
                double pointedDist = eyePos.squareDistanceTo(hitVec);

                if (pointedDist < distance || mc.objectMouseOver == null)
                {
                    mc.objectMouseOver = result;

                    if (pointedEntity instanceof LivingEntity)
                    {
                        this.extendedPointedEntity = pointedEntity;
                    }
                }
            }
        }
    }
}
