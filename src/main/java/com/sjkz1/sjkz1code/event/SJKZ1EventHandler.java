package com.sjkz1.sjkz1code.event;

import com.sjkz1.sjkz1code.core.SJKZ1CodeMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SJKZ1EventHandler 
{

	public Minecraft mc;
	public SJKZ1EventHandler() 
	{
		this.mc = Minecraft.getInstance();
	}

	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event)
	{
	
		MainMenuScreen menu = (MainMenuScreen) event.getGui();
		if(SJKZ1CodeMod.isMookBirhtDay())
		{
			menu.splashText = "Happy birthday, Naruemol Prabnarai\u2764";
		}
	
	}

}

