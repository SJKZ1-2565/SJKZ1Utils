package com.sjkz1.sjkz1code.command;

import com.mojang.brigadier.CommandDispatcher;
import com.stevekung.stevekungslib.utils.GameProfileUtils;
import com.stevekung.stevekungslib.utils.client.ClientUtils;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;
import com.stevekung.stevekungslib.utils.client.command.IClientCommand;
import com.stevekung.stevekungslib.utils.client.command.IClientSuggestionProvider;

import net.minecraft.client.Minecraft;

public class PlayerPosCommnad implements IClientCommand
{
	private static Minecraft mc = Minecraft.getInstance();
	
	@Override
	public void register(CommandDispatcher<IClientSuggestionProvider> command) {
	
		command.register(ClientCommands.literal("playerpos").executes(commands -> PlayerPosCommnad.printPlayerPos()));
	}
	
	private static int printPlayerPos()
	{
		ClientUtils.printClientMessage(GameProfileUtils.getUsername().toString() + " X: " + mc.player.getPosition().getX() + " Y: " + mc.player.getPosition().getY() + " Z: " + mc.player.getPosition().getZ());
		return 1;
	}

}
