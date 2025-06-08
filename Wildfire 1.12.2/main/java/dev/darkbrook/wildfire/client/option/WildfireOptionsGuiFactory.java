package dev.darkbrook.wildfire.client.option;

import java.util.HashSet;
import java.util.Set;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class WildfireOptionsGuiFactory implements IModGuiFactory
{
	public static final String CANONICAL_NAME = "dev.darkbrook.wildfire.client.option.WildfireOptionsGuiFactory";
	
	@Override
	public void initialize(Minecraft minecraft) {}

	@Override
	public boolean hasConfigGui()
	{
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parent)
	{
		return Wildfire.getInstance().getOptions().createConfigGui(parent);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return new HashSet<>();
	}
}
