package com.sjkz1.sjkz1code.core;

import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.event.SJKZ1EventHandler;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
/**
 * @author SJKZ1
 *
 */
@Mod(SJKZ1CodeMod.MOD_ID)
public class SJKZ1CodeMod 
{
	public static final String MOD_ID = "sjkz1code";
	public static final LoggerBase LOGGER = new LoggerBase("SJKZ1Code");
	public SJKZ1CodeMod() 
	{
		CommonUtils.addModListener(this::phaseOne);
	}
	private void phaseOne(FMLClientSetupEvent event)
    {
		SJKZ1KeyBinding.init();
        CommonUtils.registerEventHandler(new SJKZ1EventHandler());
    }
}

