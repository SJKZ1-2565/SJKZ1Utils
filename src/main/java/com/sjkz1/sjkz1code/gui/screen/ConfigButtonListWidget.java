package com.sjkz1.sjkz1code.gui.screen;
import java.util.List;


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.sjkz1.sjkz1code.config.SJKZ1CodeSettings;
import com.stevekung.stevekungslib.utils.config.AbstractSettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.list.AbstractOptionList;


/**
 * @author SteveKunG
 *
 */
public class ConfigButtonListWidget extends AbstractOptionList<ConfigButtonListWidget.ButtonItem>
{
    public ConfigButtonListWidget(int x, int y, int top, int bottom, int itemHeight)
    {
        super(Minecraft.getInstance(), x, y, top, bottom, itemHeight);
        this.centerListVertically = false;
    }

    @Override
    public int getRowWidth()
    {
        return 400;
    }

    @Override
    protected int getScrollbarPosition()
    {
        return super.getScrollbarPosition() + 32;
    }

    public void addButton(AbstractSettings<SJKZ1CodeSettings> config1, AbstractSettings<SJKZ1CodeSettings> config2)
    {
        this.addEntry(ConfigButtonListWidget.ButtonItem.createItems(this.width, config1, config2));
    }

    public void addAll(List<AbstractSettings<SJKZ1CodeSettings>> config)
    {
        for (int i = 0; i < config.size(); i += 2)
        {
            this.addButton(config.get(i), i < config.size() - 1 ? config.get(i + 1) : null);
        }
    }

    public static class ButtonItem extends AbstractOptionList.Entry<ButtonItem>
    {
        private final List<Widget> buttons;

        private ButtonItem(List<Widget> list)
        {
            this.buttons = list;
        }

        @Override
        public void render(MatrixStack matrixStack, int index, int rowTop, int rowLeft, int rowWidth, int itemHeight, int mouseX, int mouseY, boolean isSelected, float partialTicks)
        {
            for (Widget button : this.buttons)
            {
                button.y = rowTop;
                button.render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }

        @Override
        public List<? extends IGuiEventListener> getEventListeners()
        {
            return this.buttons;
        }

        public static ConfigButtonListWidget.ButtonItem createItems(int x, AbstractSettings<SJKZ1CodeSettings> configOpt1, AbstractSettings<SJKZ1CodeSettings> configOpt2)
        {
            Widget button = configOpt1.createWidget(SJKZ1CodeSettings.INSTANCE, x / 2 - 155, 0, 150);
            return configOpt2 == null ? new ConfigButtonListWidget.ButtonItem(ImmutableList.of(button)) : new ConfigButtonListWidget.ButtonItem(ImmutableList.of(button, configOpt2.createWidget(SJKZ1CodeSettings.INSTANCE, x / 2 - 155 + 160, 0, 150)));
        }
    }
}
