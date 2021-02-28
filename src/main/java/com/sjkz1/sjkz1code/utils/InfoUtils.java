package com.sjkz1.sjkz1code.utils;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

public class InfoUtils 
{
	public static final InfoUtils INSTANCE = new InfoUtils();
	public Entity extendedPointedEntity;
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
	
	@Nullable
	public MobEntity getNearestMobEntity(PlayerEntity player)
    {
        Vector3d lookVec = player.getLookVec().normalize();
        Vector3d targetPos = player.getPositionVec().add(lookVec.x, 1, lookVec.z);
        List<CreeperEntity> mobEntity = player.world.getEntitiesWithinAABB(CreeperEntity.class, new AxisAlignedBB(targetPos.subtract(10, 10, 10), targetPos.add(10, 10, 10)));
        if(mobEntity.size() > 0)
        {
            float closestDistance = 10;
            CreeperEntity entity = null;
            for(CreeperEntity mobEntities : mobEntity)
            {
                float distance = player.getDistance(mobEntities);
                if(distance < closestDistance || closestDistance == 10F)
                {
                    closestDistance = distance;
                    entity = mobEntities;
                }
            }
            return entity;
        }
        return null;
    }

}
