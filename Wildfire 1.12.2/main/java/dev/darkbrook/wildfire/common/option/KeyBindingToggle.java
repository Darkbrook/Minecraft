package dev.darkbrook.wildfire.common.option;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeyBindingToggle extends KeyBinding
{
	private final Option<Boolean> option;

	private boolean wasKeyDown;

	public KeyBindingToggle(String category, String description, IKeyConflictContext context,
			int keyCode, Option<Boolean> option)
	{
		super(String.format("key.%s.%s", category, description), context, keyCode, 
			String.format("key.%s", category));
		this.option = option;
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.registerKeyBinding(this);
	}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END)
			return;

		boolean isKeyDown = isKeyDown();

		if (isKeyDown && !wasKeyDown)
			option.set(!option.get());

		wasKeyDown = isKeyDown;
	}
}
