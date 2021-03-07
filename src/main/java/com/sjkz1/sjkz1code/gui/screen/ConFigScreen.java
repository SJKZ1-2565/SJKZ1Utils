package com.sjkz1.sjkz1code.gui.screen;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.stevekung.stevekungslib.utils.LangUtils;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

/**
 * @author SteveKunG
 *
 */
public class ConfigScreen  extends Screen
{
	public ConfigScreen()
	{
		super(StringTextComponent.EMPTY);
	}

	@Override
	public void init()
	{
		this.addButton(new Button(this.width / 2 - 75, this.height / 6 + 27, 150, 20, LangUtils.translate("menu.config.title"), button ->
		{
			SJKZ1CodeSettings.INSTANCE.save();
			this.minecraft.displayGuiScreen(new ConfigSettings(this));
		}));
		this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 175, 160, 20, DialogTexts.GUI_DONE, button ->
		{
			SJKZ1CodeSettings.INSTANCE.save();
			this.minecraft.displayGuiScreen(null);
		}));
		this.addButton(new Button(this.width / 2 - 160, this.height / 6 + 175, 160, 20, LangUtils.translate("menu.reset_config"), button ->
		{
			SJKZ1CodeSettings.INSTANCE.save();
			this.minecraft.displayGuiScreen(new ConfirmScreen(this::resetConfig, LangUtils.translate("menu.reset_config_confirm"), StringTextComponent.EMPTY));
		}));
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		SJKZ1CodeSettings.INSTANCE.save();
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		AbstractGui.drawCenteredString(matrixStack, this.font, LangUtils.translate("menu.main.title").deepCopy().appendString(" : ").append(LangUtils.formatted("menu.current_selected_profile", TextFormatting.YELLOW, SJKZ1CodeSettings.CURRENT_PROFILE)), this.width / 2, 10, 16777215);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
	}

	private void resetConfig(boolean condition)
	{
		if (condition)
		{
			SJKZ1CodeSettings.resetConfig();
			this.minecraft.displayGuiScreen(this);
		}
		else
		{
			this.minecraft.displayGuiScreen(this);
		}
	}
}
