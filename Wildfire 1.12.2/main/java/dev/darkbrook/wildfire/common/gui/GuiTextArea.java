package dev.darkbrook.wildfire.common.gui;

import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class GuiTextArea extends Gui
{
    private final int id;
    private final FontRenderer fontRenderer;
	public final Dimensions dimensions = new Dimensions();
	public Theme theme = new Theme();
    public String text = "";
    
    public GuiTextArea(int id, FontRenderer fontRenderer, int x, int y, int width, int height)
	{
    	this.id = id;
    	this.fontRenderer = fontRenderer;
    	dimensions.x = x;
    	dimensions.y = y;
    	dimensions.width = width;
    	dimensions.height = height;
	}
    
    public void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	
    }
    
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		
	}

	public void drawTextArea()
    {
		dimensions.drawOutline();
		dimensions.drawBackground();
		dimensions.drawText();
    }
	
	public class Dimensions
	{
	    public int x;
	    public int y;
	    public int width;
	    public int height;
	    public int outlineWidth = 1;
	    public int textPadding = 4;
	    
		protected void drawOutline()
		{
			drawRect(x - outlineWidth, y - outlineWidth, x + width + outlineWidth, y + height + outlineWidth, theme.outline);
		}

		protected void drawBackground()
		{
			drawRect(x, y, x + width, y + height, theme.background);
		}

		protected void drawText()
		{
			fontRenderer.drawSplitString(text, x + textPadding, y + textPadding, width - (textPadding * 2), theme.text);
		}
	}
	
	public static class Theme
	{
		public final int outline = -6250336;
		public final int background = -16777216;
		public final int text = 14737632;
	}
}
