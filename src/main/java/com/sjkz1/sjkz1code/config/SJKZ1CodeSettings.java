package com.sjkz1.sjkz1code.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.sjkz1.sjkz1code.core.SJKZ1CodeMod;
import com.stevekung.stevekungslib.utils.GameProfileUtils;
import com.stevekung.stevekungslib.utils.LangUtils;
import com.stevekung.stevekungslib.utils.client.ClientUtils;
import com.stevekung.stevekungslib.utils.config.BooleanSettings;
import com.stevekung.stevekungslib.utils.config.Settings;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.common.util.Constants;
public class SJKZ1CodeSettings extends Settings
{
	public static SJKZ1CodeSettings INSTANCE = new SJKZ1CodeSettings();

	public static final File SJKZ1_DIR = new File(Minecraft.getInstance().gameDir, "sjkz1");
	public static final File USER_DIR = new File(SJKZ1CodeSettings.SJKZ1_DIR, GameProfileUtils.getUUID().toString());
	public static final File DEFAULT_CONFIG_FILE = new File(SJKZ1CodeSettings.USER_DIR, "default.dat");
	public static String CURRENT_PROFILE = "";
	private static File PROFILE_FILE;

	public boolean disableFireOverlay = true;
	public boolean loginToast = true;
	public boolean playSoundWhenProjectileHitTheEntity = false;
	public boolean autoRespawn = false;
	public boolean HealtStatus = false;


	public static final BooleanSettings<SJKZ1CodeSettings> DISABLE_FIRE_OVERLAY = new BooleanSettings<>("sjkz1_setting.disableFireOverlay", config -> config.disableFireOverlay, (config, value) -> config.disableFireOverlay = value);
	public static final BooleanSettings<SJKZ1CodeSettings> PLAYSOUND_WHEN_PROJECTILE_HIT_THE_ENTITY = new BooleanSettings<>("sjkz1_setting.playSoundWhenProjectileHitTheEntity", config -> config.playSoundWhenProjectileHitTheEntity, (config, value) -> config.playSoundWhenProjectileHitTheEntity = value);
	public static final BooleanSettings<SJKZ1CodeSettings> AUTO_RESPAWN = new BooleanSettings<>("sjkz1_setting.autoRespawn", config -> config.autoRespawn, (config, value) -> config.autoRespawn = value);
	public static final BooleanSettings<SJKZ1CodeSettings> LOG_IN_TOAST = new BooleanSettings<>("sjkz1_setting.loginToast", config -> config.loginToast, (config, value) -> config.loginToast = value);
	public static final BooleanSettings<SJKZ1CodeSettings> HEALTH_STATUS = new BooleanSettings<>("sjkz1_setting.HealtStatus", config -> config.HealtStatus, (config, value) -> config.HealtStatus = value);

	private SJKZ1CodeSettings() {}

	public static void setCurrentProfile(String profileName)
	{
		SJKZ1CodeSettings.PROFILE_FILE = new File(USER_DIR, profileName + ".dat");
		SJKZ1CodeSettings.CURRENT_PROFILE = profileName;
	}

	public void load()
	{
		try
		{
			CompoundNBT nbt = CompressedStreamTools.read(SJKZ1CodeSettings.PROFILE_FILE);

			if (nbt == null)
			{
				return;
			}
			this.disableFireOverlay = this.getBoolean(nbt, "DisableFireOverlay", this.disableFireOverlay);
			this.playSoundWhenProjectileHitTheEntity = this.getBoolean(nbt, "PlaySoundWhenProjectileHitTheTarget", this.playSoundWhenProjectileHitTheEntity);
			this.autoRespawn = this.getBoolean(nbt, "AutoRespawn", this.autoRespawn);
			this.loginToast = this.getBoolean(nbt, "LoginToast", this.loginToast);
			this.HealtStatus = this.getBoolean(nbt, "HealtStatus", this.HealtStatus);
			SJKZ1CodeMod.LOGGER.info("Loading extended config {}", SJKZ1CodeSettings.PROFILE_FILE.getPath());
		}
		catch (Exception e) {}
	}

	@Override
	public void save()
	{
		this.save(!SJKZ1CodeSettings.CURRENT_PROFILE.isEmpty() ? SJKZ1CodeSettings.CURRENT_PROFILE : "default");
	}

	public void save(String profileName)
	{
		try
		{
			CompoundNBT nbt = new CompoundNBT();
			nbt.putBoolean("DisableFireOverlay", this.disableFireOverlay);
			nbt.putBoolean("PlaySoundWhenProjectileHitTheTarget", this.playSoundWhenProjectileHitTheEntity);
			nbt.putBoolean("AutoRespawn", this.autoRespawn);
			nbt.putBoolean("LoginToast", this.loginToast);
			nbt.putBoolean("HealtStatus", this.HealtStatus);



			CompressedStreamTools.write(nbt, !profileName.equalsIgnoreCase("default") ? new File(USER_DIR, profileName + ".dat") : SJKZ1CodeSettings.PROFILE_FILE);
		}
		catch (Exception e) {}
	}

	public static void saveProfileFile(String profileName)
	{
		File profile = new File(SJKZ1CodeSettings.USER_DIR, "profile.txt");

		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(profile), StandardCharsets.UTF_8)))
		{
			writer.println("profile:" + profileName);
			SJKZ1CodeMod.LOGGER.info("Saving profile name!");
		}
		catch (IOException e)
		{
			SJKZ1CodeMod.LOGGER.error("Failed to save profile", (Throwable)e);
		}
	}

	public static void resetConfig()
	{
		SJKZ1CodeSettings.INSTANCE = new SJKZ1CodeSettings();
		SJKZ1CodeSettings.INSTANCE.save(SJKZ1CodeSettings.CURRENT_PROFILE);
		ClientUtils.printClientMessage(LangUtils.translate("misc.config.reset_config", SJKZ1CodeSettings.CURRENT_PROFILE));
	}

	private boolean getBoolean(CompoundNBT nbt, String key, boolean defaultValue)
	{
		if (nbt.contains(key, Constants.NBT.TAG_ANY_NUMERIC))
		{
			return nbt.getBoolean(key);
		}
		else
		{
			return defaultValue;
		}
	}
}
