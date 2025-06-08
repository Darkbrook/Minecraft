package dev.darkbrook.wildfire.mixin.command;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.darkbrook.wildfire.Wildfire;
import net.minecraft.util.TabCompleter;
import net.minecraftforge.client.ClientCommandHandler;

@Mixin(TabCompleter.class)
public abstract class TabCompleterMixin
{
	@Shadow
	protected boolean requestedCompletions;

	@Inject(method = "requestCompletions", at = @At(
			target = "Lnet/minecraftforge/client/ClientCommandHandler;autoComplete(Ljava/lang/String;)V",
			value = "INVOKE", shift = Shift.AFTER), cancellable = true)
	private void requestClientCompletions(String prefix, CallbackInfo callback)
	{
		// Don't override completion for command name suggestions
		if (!prefix.contains(" "))
			return;
		
		ClientCommandHandler handler = ClientCommandHandler.instance;
		String[] latestAutoComplete = handler.latestAutoComplete;

		if (latestAutoComplete == null)
			return;

		callback.cancel();
		requestedCompletions = true;
		handler.latestAutoComplete = null;
		setCompletions(latestAutoComplete);
		Wildfire.LOGGER.debug("Tab completions overriden by local suggestions");
	}

	@Shadow
	protected abstract void setCompletions(String... newCompl);
}
