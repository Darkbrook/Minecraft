package com.darkbrook.common.tileentity;

import java.util.Random;

import com.darkbrook.common.nbt.NBTBaseType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public abstract class TileEntityBase extends TileEntity implements IInventory, IInteractionObject
{
	protected static final Random random = new Random();

	protected ItemStack[] contents = new ItemStack[getSizeInventory()];
	private ITileEntityField[] fields;
	private String customName;

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return contents[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) 
	{
		ItemStack stack = contents[slot];
		
		if (stack != null)
		{
			if (stack.stackSize > count)
			{
				ItemStack split = stack.splitStack(count);
				
				if (stack.stackSize == 0)
					contents[slot] = null;

				return split;
			}
			else
				contents[slot] = null;
		}		
		
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		ItemStack stack = contents[slot];
		contents[slot] = null;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		contents[slot] = stack;
	}
	
	@Override
	public String getName() 
	{
		return hasCustomName() ? customName : "container." + getUnlocalizedName();
	}
	
	public abstract String getUnlocalizedName();

	public void setCustomName(String name)
	{
		customName = name;
	}
	
	@Override
	public boolean hasCustomName() 
	{
		return customName != null && !customName.isEmpty();
	}
	
	@Override
	public IChatComponent getDisplayName() 
	{
		return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		clear();
		NBTTagList items = compound.getTagList("Items", NBTBaseType.COMPOUND);
		
		for (int i = 0; i < items.tagCount(); i++)
		{
			NBTTagCompound item = items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if (slot >= 0 && slot < contents.length)
				contents[slot] = ItemStack.loadItemStackFromNBT(item);
		}
		
		for (ITileEntityField field : fields)
			field.readFromNBT(compound);
		
		if (compound.hasKey("CustomName", NBTBaseType.STRING))
			customName = compound.getString("CustomName");
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);	
		
		for (ITileEntityField field : fields)
			field.writeToNBT(compound);
		
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < contents.length; i++)
		{
			ItemStack stack = contents[i];
			
			if (stack == null)
				continue;
			
			NBTTagCompound item = new NBTTagCompound();
			item.setByte("Slot", (byte) i);
			stack.writeToNBT(item);
			items.appendTag(item);
		}
		
		compound.setTag("Items", items);
		
		if (hasCustomName())
			compound.setString("CustomName", customName);
	}
	
	public int getInventoryStackLimit(ItemStack stack)
	{
		return Math.min(getInventoryStackLimit(), stack.getMaxStackSize());
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) 
	{
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64.0d;
	}

	@Override
	public void openInventory(EntityPlayer player) 
	{
		
	}

	@Override
	public void closeInventory(EntityPlayer player) 
	{		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		return true;
	}

	protected void assignFields()
	{
		fields = createFields();
	}
	
	protected abstract ITileEntityField[] createFields();
	
	@Override
	public int getField(int id) 
	{
		return isValidField(id) ? fields[id].getMetadata() : 0;
	}

	@Override
	public void setField(int id, int data) 
	{
		if (isValidField(id))
			fields[id].setFromMetadata(data);
	}
	
	public boolean isValidField(int id)
	{
		return id >= 0 && id < getFieldCount();
	}

	@Override
	public int getFieldCount() 
	{
		return fields.length;
	}

	@Override
	public void clear() 
	{
		contents = new ItemStack[getSizeInventory()];		
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
}
