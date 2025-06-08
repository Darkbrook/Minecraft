package com.darkbrook.prospector.client.gui;

import com.darkbrook.prospector.Prospector;
import com.darkbrook.prospector.inventory.ContainerSluiceBox;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSluiceBox extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Prospector.MODID, "textures/gui/container/sluice_box.png");
	
	private final IInventory inventory;
	private final InventoryPlayer playerInventory;

	public GuiSluiceBox(IInventory inventory, InventoryPlayer playerInventory) 
	{
		super(new ContainerSluiceBox(inventory, playerInventory));
		this.inventory = inventory;
		this.playerInventory = playerInventory;
		ySize = 133;
	}
	
	@Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
		String name = inventory.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(name, (xSize / 2) - (fontRendererObj.getStringWidth(name) / 2), 6, 4210752);
		fontRendererObj.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 94, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;		
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 59, y + 19, 176, 0, getSluiceProgressScaled(24) + 1, 17);
	}
	
	private int getSluiceProgressScaled(int pixels)
	{
		int progress = inventory.getField(0);
		int total = inventory.getField(1);
		return progress != 0 && total != 0 ? progress * pixels / total : 0;
	}
}
