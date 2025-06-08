package dev.darkbrook.wildfire.client.option;

import java.nio.file.Path;

import org.lwjgl.input.Keyboard;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.common.option.KeyBindingToggle;
import dev.darkbrook.wildfire.common.option.Option;
import dev.darkbrook.wildfire.common.option.OptionConfig;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;

public class WildfireOptions extends OptionConfig
{
	// Command Block
	private final Option<Boolean> customInterface;
	private final Option<Boolean> previewCommandBlocks;
	private final Option<Integer> softWrapLimit;
	private final Option<Integer> softWrapBuffer;
	private final Option<Integer> hardWrapLimit;

	// Fullbright
	private final Option<Boolean> fullbrightEnabled;
	private final Option<FullbrightMode> fullbrightMode;
	private final Option<Integer> maxSkyLight;
	private final Option<Integer> minSkyLight;
	
	// Hud
	private final Option<TitlePosition> titlePosition;
	private final Option<Integer> titleScale;
	private final Option<Boolean> hideHudStatusEffects;
	private final Option<Boolean> hideScoreboardPoints;
	
	// General
	private final Option<Integer> labelScale;
	private final Option<Boolean> customStackSizes;

	public WildfireOptions(Path path)
	{
		super(Wildfire.MODID, path);
		
		addCategory("commandBlock");
		customInterface = addOption("customInterface", true);
		previewCommandBlocks = addOption("previewCommand", true);
		softWrapLimit = addOption("softWrapLimit", 32);
		softWrapBuffer = addOption("softWrapBuffer", 16);
		hardWrapLimit = addOption("hardWrapLimit", 64);

		addCategory("fullbright");
		fullbrightEnabled = addOption("enabled", false);
		fullbrightMode = addOption("mode", FullbrightMode.SKY_LIGHT);
		minSkyLight = addOption("minSkyLight", 100, 0, 100, true);
		maxSkyLight = addOption("maxSkyLight", 100, 0, 100, true);
		
		addCategory("hud");
		titlePosition = addOption("titlePosition", TitlePosition.TOP);
		titleScale = addOption("titleScale", 75, 25, 100, true);
		hideHudStatusEffects = addOption("hideStatusEffects", false);
		hideScoreboardPoints = addOption("hideScoreboardPoints", true);
		
		addCategory("misc");
		labelScale = addOption("labelScale", 75, 50, 100, true);
		customStackSizes = addOption("customStackSizes", false);
		
		// Makes sure option properties are ordered by declaration
		reorderProperties();
		// Writes default values to disk
		save();
		
		addKeyBinding(fullbrightEnabled, KeyConflictContext.IN_GAME, Keyboard.KEY_B);
	}

	private Option<Integer> addOption(String name, Integer defaultValue, int minValue, int maxValue, boolean slider)
	{
		return addOption(name, defaultValue).setConstraints(p -> 
				p.setMinValue(minValue).setMaxValue(maxValue).setHasSlidingControl(slider));
	}
	
	private void addKeyBinding(Option<Boolean> option, IKeyConflictContext context, int keyCode)
	{
		new KeyBindingToggle(Wildfire.MODID, option.getName(), context, keyCode, option);
	}
	
	// Command Block
	
	public boolean customInterface()
	{
		return customInterface.get();
	}
	
	public boolean previewCommandBlocks()
	{
		return previewCommandBlocks.get();
	}
	
	public int getSoftWrapLimit()
	{
		return softWrapLimit.get();
	}
	
	public int getSoftWrapBuffer()
	{
		return softWrapBuffer.get();
	}
	
	public int getHardWrapLimit()
	{
		return hardWrapLimit.get();
	}
	
	// Fullbright
	
	public boolean useSkyLightFullbright()
	{
		return fullbrightEnabled.get() && fullbrightMode.get() == FullbrightMode.SKY_LIGHT;
	}

	public boolean useNightVisionFullbright()
	{
		return fullbrightEnabled.get() && fullbrightMode.get() == FullbrightMode.NIGHT_VISION;
	}
	
	public float getMinSkyLight()
	{
		return minSkyLight.get() / 100.0f;
	}
	
	public float getMaxSkyLight()
	{
		return maxSkyLight.get() / 100.0f;
	}
	
	// Hud
	
	public TitlePosition getTitlePosition()
	{
		return titlePosition.get();
	}
	
	public float getTitleScale()
	{
		return titleScale.get() / 100.0f;
	}
	
	public boolean hideHudStatusEffects()
	{
		return hideHudStatusEffects.get();
	}
	
	public boolean hideScoreboardPoints()
	{
		return hideScoreboardPoints.get();
	}
	
	// Misc
	
	public float getLabelScale()
	{
		return labelScale.get() / 100.0f;
	}

	public boolean useCustomStackSizes()
	{
		return customStackSizes.get();
	}
	
	public enum FullbrightMode
	{
		SKY_LIGHT, NIGHT_VISION;
	}
	
	public enum TitlePosition
	{
		TOP, CENTER;
	}
}
