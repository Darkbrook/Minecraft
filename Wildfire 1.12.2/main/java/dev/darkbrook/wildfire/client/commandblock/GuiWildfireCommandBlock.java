package dev.darkbrook.wildfire.client.commandblock;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import dev.darkbrook.wildfire.common.gui.GuiTextArea;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.ITabCompleter;

public class GuiWildfireCommandBlock extends GuiScreen implements ITabCompleter
{
	private final TileEntityCommandBlock block;
	private GuiTextArea textArea;
	
	public GuiWildfireCommandBlock(TileEntityCommandBlock block)
	{
		this.block = block;
	}
	
	@Override
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
		int xSize = 300;
		int ySize = 200;
		int left = (this.width - xSize) / 2;
		int top = (this.height - ySize) / 2;
        textArea = new GuiTextArea(0, fontRenderer, left, top, xSize, ySize);
        textArea.text = block.getCommandBlockLogic().getCommand();
    }
	
	@Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		textArea.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
		drawDefaultBackground();
		textArea.drawTextArea();
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	@Override
	public void setCompletions(String... newCompletions)
	{
		
	}
}
