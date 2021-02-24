package com.sjkz1.sjkz1code.core.key;

import org.lwjgl.glfw.GLFW;

import com.sjkz1.sjkz1code.core.SJKZ1CodeMod;
import com.stevekung.stevekungslib.keybinding.KeyBindingBase;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class SJKZ1KeyBinding 
{
	 public static KeyBinding OPEN_CONFIG;
	 public static KeyBinding DANCE;

	    public static void init()
	    {
	    	SJKZ1KeyBinding.OPEN_CONFIG = new KeyBindingBase("key.config.desc", SJKZ1CodeMod.MOD_ID, GLFW.GLFW_KEY_R);
	    	SJKZ1KeyBinding.DANCE = new KeyBindingBase("key.dance.desc", SJKZ1CodeMod.MOD_ID, GLFW.GLFW_KEY_O);
	        ClientRegistry.registerKeyBinding(SJKZ1KeyBinding.OPEN_CONFIG);
	        ClientRegistry.registerKeyBinding(SJKZ1KeyBinding.DANCE);
	    }
}
