package dev.darkbrook.wildfire.client.commandblock;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import dev.darkbrook.wildfire.Wildfire;
import dev.darkbrook.wildfire.client.option.WildfireOptions;
import dev.darkbrook.wildfire.mixin.commandblock.AbstractClientPlayerInvoker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiCommandBlock;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class CommandBlockHandler
{
	private static final Wildfire WF = Wildfire.getInstance();
	private static final Minecraft MC = Minecraft.getMinecraft();
	
	private TileEntityCommandBlock lookingAt;
	private String command;
	private List<String> preview;
	private boolean isUpdated;

	public CommandBlockHandler()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		if (event.phase != Phase.START)
			return;
		
		if (lookingAt == null)
			isUpdated = false;
		
		lookingAt = getLookingAt();
	}
	
	@SubscribeEvent
	public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Pre event)
	{
		// Done button pressed
		if (event.getGui() instanceof GuiCommandBlock && event.getButton().id == 0)
    		lookingAt = null;
	}

	@SubscribeEvent
	public void onKeyInput(GuiScreenEvent.KeyboardInputEvent.Pre event)
	{
		if (!(MC.currentScreen instanceof GuiCommandBlock))
			return;
		
		int keyCode = Keyboard.getEventKey();
		
		// Enter key pressed
		if (keyCode == 28 || keyCode == 156)
    		lookingAt = null;
	}
	
	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent.Post event)
	{
		if (event.getType() != ElementType.TEXT || lookingAt == null)
			return;
        
		String command = lookingAt.getCommandBlockLogic().getCommand();
		
		if (this.command != command)
		{
			this.command = command;
			WildfireOptions options = WF.getOptions();
			preview = getWrappedText(command, options.getSoftWrapLimit(), 
					options.getSoftWrapBuffer(),
					options.getHardWrapLimit());
		}
		
        renderPreview(MC.fontRenderer, event.getResolution().getScaledWidth());
	}
	
	private TileEntityCommandBlock getLookingAt()
	{
		EntityPlayerSP player = MC.player;
		
		if (player == null)
			return null;
		
		NetworkPlayerInfo playerInfo = ((AbstractClientPlayerInvoker) player).invokeGetPlayerInfo();
		
		if (playerInfo == null || playerInfo.getGameType().isSurvivalOrAdventure())
			return null;
		
		RayTraceResult ray = MC.objectMouseOver;
		
        if (ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK || ray.getBlockPos() == null)
	       return null;
        
        BlockPos pos = ray.getBlockPos();
        TileEntity entity = MC.world.getTileEntity(pos);
        
        if (!(entity instanceof TileEntityCommandBlock))
        	return null;
        
        if ((lookingAt != entity || !isUpdated) && player.canUseCommandBlock()
        	&& (!player.isSneaking() || (player.getHeldItemMainhand().isEmpty() && player.getHeldItemOffhand().isEmpty())))
        {
        	Vec3d hitVec = ray.hitVec;
        	float x = (float) (hitVec.x - pos.getX());
        	float y = (float) (hitVec.y - pos.getY());
        	float z = (float) (hitVec.z - pos.getZ());
        	player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, ray.sideHit, EnumHand.MAIN_HAND, x, y, z));
        	isUpdated = true;
        }
        
    	return (TileEntityCommandBlock) entity;
	}
	
	private void renderPreview(FontRenderer fontRenderer, int screenWidth)
	{
	    for (int i = 0; i < preview.size(); i++)
	    {
	    	String text = preview.get(i);
	        int textWidth = fontRenderer.getStringWidth(text);
			int x = screenWidth / 2 - textWidth / 2;
			int y = (i * (fontRenderer.FONT_HEIGHT + 2)) + 2;
			Gui.drawRect(x - 1, y - 1, x + textWidth, y + fontRenderer.FONT_HEIGHT, -1873784752);
			fontRenderer.drawString(text, x, y, 14737632);
		}
	}
	
	private static List<String> getWrappedText(String text, int softLimit, int softBuffer, int hardLimit)
	{
		List<String> lines = new ArrayList<>();
	    int index = 0;
	    int length = text.length();

		while (index < length)
		{
			if (length - index <= softLimit + softBuffer)
			{
				lines.add(text.substring(index).trim());
				break;
			}

			int hardEnd = Math.min(index + hardLimit, length);
			int splitIndex = text.indexOf(' ', index + softLimit);

			if (splitIndex == -1 || splitIndex >= hardEnd)
				splitIndex = hardEnd;

			lines.add(text.substring(index, splitIndex).trim());
		    index = splitIndex < length && text.charAt(splitIndex) == ' ' 
		    	? splitIndex + 1 : splitIndex;
		}
		
		return lines;
	}
}
