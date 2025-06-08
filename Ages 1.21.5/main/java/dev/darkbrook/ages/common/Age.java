package dev.darkbrook.ages.common;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.ImmutableSet;

import dev.darkbrook.ages.Ages;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record Age(String id, Text displayName, Set<Item> items, Set<Block> blocks, Set<Interaction> interactions)
{
	public static Age fromJson(Json json)
	{
		ImmutableSet.Builder<Item> itemBuilder = builderWithExpectedSize(json.unlocks());
		ImmutableSet.Builder<Block> blockBuilder = ImmutableSet.builder();
		ImmutableSet.Builder<Interaction> interactionBuilder = builderWithExpectedSize(json.interactions());

		Optional.ofNullable(json.unlocks())
			.ifPresent(unlocks -> Arrays.stream(unlocks)
				.map(unlock -> Registries.ITEM.get(Identifier.ofVanilla(unlock)))
				.forEach(item -> 
				{
					itemBuilder.add(item);

					if (item instanceof BlockItem blockItem)
						blockBuilder.add(blockItem.getBlock());
				}));
		
		Optional.ofNullable(json.interactions())
			.ifPresent(interactions -> Arrays.stream(interactions)
				.forEach(id -> 
				{
					Interaction interaction = Interaction.fromId(id);

					if (interaction != null)
						interactionBuilder.add(interaction);
					else
						Ages.LOGGER.warn("Unknown interaction id: {} for age: {}", id, json.id());
				}));
				
		return new Age(json.id(), json.display_name(), 
			itemBuilder.build(), blockBuilder.build(), interactionBuilder.build());
	}
	
	private static <T> ImmutableSet.Builder<T> builderWithExpectedSize(@Nullable Object[] array)
	{
		return array != null
			? ImmutableSet.builderWithExpectedSize(array.length)
			: ImmutableSet.builder();
	}

	public record Json(String id, Text display_name, @Nullable String[] unlocks, @Nullable String[] interactions) {}
}
