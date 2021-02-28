package com.sjkz1.sjkz1code.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.text.TextFormatting;

public class EntityUtils 
{
	private static final Minecraft mc = Minecraft.getInstance();
	
	public static void setGlowing(Entity entity, TextFormatting color, String teamName) {
        ScorePlayerTeam team = (mc.world.getScoreboard().getTeamNames().contains(teamName) ?
                mc.world.getScoreboard().getTeam(teamName) :
                mc.world.getScoreboard().createTeam(teamName));

        mc.world.getScoreboard().addPlayerToTeam(
                entity instanceof PlayerEntity ? entity.getDisplayName().getString() : entity.getCachedUniqueIdString(), team);
        mc.world.getScoreboard().getTeam(teamName).setColor(color);

        entity.setGlowing(true);
    }
}
