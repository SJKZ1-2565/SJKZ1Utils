package com.sjkz1.sjkz1code.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import com.sjkz1.sjkz1code.command.PlayerPosCommand;
import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.sjkz1.sjkz1code.core.key.SJKZ1KeyBinding;
import com.sjkz1.sjkz1code.event.SJKZ1EventHandler;
import com.stevekung.stevekungslib.utils.CommonUtils;
import com.stevekung.stevekungslib.utils.LoggerBase;
import com.stevekung.stevekungslib.utils.client.command.ClientCommands;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
/**
 * @author SJKZ1
 * @version 1.0.2
 */
@Mod(SJKZ1CodeMod.MOD_ID)
public class SJKZ1CodeMod 
{
	public static final String MOD_ID = "sjkz1code";
	public static final String VERSION = "1.0.2";
	public static final String NAME = "SJKZ1Code";
	private static final File PROFILE = new File(SJKZ1CodeSettings.USER_DIR, "profile.txt");
	private static final Splitter COLON_SPLITTER = Splitter.on(':').limit(2);
	public static final LoggerBase LOGGER = new LoggerBase("SJKZ1Code");
	static
	{
		SJKZ1CodeMod.initProfileFile();
	}
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
		SJKZ1CodeMod.loadProfileOption();
		LOGGER.info("Successfully setup mod!");
	}
	private void setupCommands()
	{
		ClientCommands.register(new PlayerPosCommand());
		LOGGER.info("Successfully setup command");
	}
	private static void loadProfileOption()
	{
		if (!PROFILE.exists())
		{
			return;
		}
		if (!SJKZ1CodeSettings.DEFAULT_CONFIG_FILE.exists())
		{
			SJKZ1CodeMod.LOGGER.info("Creating default profile...");
			SJKZ1CodeSettings.setCurrentProfile("default");
			SJKZ1CodeSettings.INSTANCE.save();
		}

		CompoundNBT nbt = new CompoundNBT();

		try (BufferedReader reader = Files.newReader(PROFILE, Charsets.UTF_8))
		{
			reader.lines().forEach(option ->
			{
				try
				{
					Iterator<String> iterator = SJKZ1CodeMod.COLON_SPLITTER.split(option).iterator();
					nbt.putString(iterator.next(), iterator.next());
				}
				catch (Exception e) {}
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		for (String property : nbt.keySet())
		{
			String key = nbt.getString(property);

			if ("profile".equals(property))
			{
				SJKZ1CodeMod.LOGGER.info("Load current profile '{}'", key);
				SJKZ1CodeSettings.setCurrentProfile(key);
				SJKZ1CodeSettings.INSTANCE.load();
			}
		}
	}

	private static void initProfileFile()
	{
		if (!SJKZ1CodeSettings.SJKZ1_DIR.exists())
		{
			SJKZ1CodeSettings.SJKZ1_DIR.mkdirs();
		}
		if (!SJKZ1CodeSettings.USER_DIR.exists())
		{
			SJKZ1CodeSettings.USER_DIR.mkdirs();
		}

		if (!SJKZ1CodeMod.PROFILE.exists())
		{
			try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(SJKZ1CodeMod.PROFILE), StandardCharsets.UTF_8)))
			{
				writer.println("profile:default");
				SJKZ1CodeMod.LOGGER.info("Creating profile option at {}", SJKZ1CodeMod.PROFILE.getPath());
			}
			catch (IOException e)
			{
				SJKZ1CodeMod.LOGGER.error("Failed to save profile");
				e.printStackTrace();
			}
		}
	}
}

