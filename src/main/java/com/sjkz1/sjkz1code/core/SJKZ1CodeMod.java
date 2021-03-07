package com.sjkz1.sjkz1code.core;

import com.sjkz1.sjkz1code.command.PlayerPosCommand;
import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.event.SJKZ1EventHandler;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
/**
 * @author SJKZ1
 * @version 1.0.1
 */
@Mod(SJKZ1CodeMod.MOD_ID)
public class SJKZ1CodeMod 
{
	public static final String MOD_ID = "sjkz1code";
	public static final String VERSION = "1.0.1";
	public static final LoggerBase LOGGER = new LoggerBase("SJKZ1Code");
	public SJKZ1CodeMod() 
	{
		CommonUtils.addModListener(this::phaseOne);
		LOGGER.info("Preparing setup mod...");
	}
	private void phaseOne(FMLClientSetupEvent event)
    {
		this.setupCommands();
		SJKZ1KeyBinding.init();
        CommonUtils.registerEventHandler(new SJKZ1EventHandler());
        LOGGER.info("Successfully setup mod!");
    }
	private void setupCommands()
	{
		ClientCommands.register(new PlayerPosCommand());
		LOGGER.info("Successfully setup command");
	}
}

