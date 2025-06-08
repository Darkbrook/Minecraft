package dev.darkbrook.ages.common;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import dev.darkbrook.ages.Ages;
import dev.darkbrook.ages.common.lang.DelimitedBuilder;
import dev.darkbrook.ages.common.lang.Plural;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class AgeProgressionManager
{
	private static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(Text.class, new Text.Serializer(DynamicRegistryManager.EMPTY))
		.setPrettyPrinting()
		.create();
	
	private static final Path DIRECTORY = FabricLoader.getInstance().getConfigDir().resolve(Ages.MOD_ID);
	private static final Path AGES_JSON = DIRECTORY.resolve("ages.json");
	private static final Path STATE_JSON = DIRECTORY.resolve("state.json");
	private static final Text UNLOCKED_TEXT = Text.literal(Formatting.GREEN + "Unlocked").styled(style -> style.withFont(Identifier.ofVanilla("ten")));

	private Map<String, Age> ageMap;
	private List<String> ageIds;
	private Age currentAge;
	
	public boolean load()
	{
		return loadAges() && loadState();
	}
	
	public boolean loadAges()
	{
		return loadJsonFile(AGES_JSON, reader ->
		{
			Map<String, Age> ageMap = new LinkedHashMap<>();
			List<String> ageIds = new ArrayList<>();
			reader.beginArray();
			while (reader.hasNext())
			{
				Age.Json json = GSON.fromJson(reader, Age.Json.class);
				Age age = Age.fromJson(json);
				Object restrictions = new DelimitedBuilder<Integer>()
					.predicate(size -> size > 0)
					.pattern("with restrictions: {0}")
					.append(age.items().size(), Plural.of(0, "item"))
					.append(age.blocks().size(), Plural.of(0, "block"))
					.append(age.interactions().size(), Plural.of(0, "interaction"))
					.emptyAlternative("with no restrictions");
				String id = age.id();
				Ages.LOGGER.info("Loaded \"{}\" {}", age.displayName().getString(), restrictions);
				ageMap.put(id, age);
				ageIds.add(id);
			}
			reader.endArray();
			this.ageMap = ageMap;
			this.ageIds = ageIds;
		});
	}
	
	public boolean loadState()
	{
		if (!Files.exists(STATE_JSON))
		{
			currentAge = getStartingAge();
			saveState();
			return true;
		}
		
		return loadJsonFile(STATE_JSON, reader ->
		{
			JsonObject json = GSON.fromJson(reader, JsonObject.class);
			String id = json.get("age").getAsString();
			currentAge = Optional.ofNullable(ageMap.get(id))
					.orElseThrow(() -> new IllegalStateException("Unknown age id: " + id));
		});
	}
	
	public boolean advanceToNextAge(MinecraftServer server)
	{
		int currentAgeIndex = ageIds.indexOf(currentAge.id());
		if (currentAgeIndex >= ageIds.size() - 1)
			return false;
		setCurrentAge(server, ageMap.get(ageIds.get(currentAgeIndex + 1)));
		return true;
	}

	public List<Age> getFutureAges()
	{
		List<Age> ages = new ArrayList<>();
		for (int i = ageIds.indexOf(currentAge.id()) + 1; i < ageIds.size(); i++)
			ages.add(ageMap.get(ageIds.get(i)));
		return ages;
	}
	
	public Optional<Age> getFutureUnlockAge(Item item)
	{
		return getFutureUnlockAge(item, Age::items);
	}
	
	public Optional<Age> getFutureUnlockAge(Block block)
	{
		return getFutureUnlockAge(block, Age::blocks);
	}
	
	public Optional<Age> getFutureUnlockAge(Interaction interaction)
	{
		return getFutureUnlockAge(interaction, Age::interactions);
	}
	
	public Optional<Age> getFutureUnlockAge(Predicate<Age> predicate)
	{
		for (int i = ageIds.indexOf(currentAge.id()) + 1; i < ageIds.size(); i++)
		{
			Age age = ageMap.get(ageIds.get(i));
			if (predicate.test(age))
				return Optional.of(age);
		}
		return Optional.empty();
	}
	
	public Optional<Age> getAge(String id)
	{
		return Optional.ofNullable(ageMap.get(id));
	}
	
	public void setCurrentAge(MinecraftServer server, Age age)
	{
		currentAge = age;
		saveState();
		broadcastAgeAdvancement(server);
	}
	
	public Collection<Text> getAgeDisplayNames()
	{
		return ageMap.values()
			.stream()
			.map(Age::displayName)
			.collect(ImmutableList.toImmutableList());
	}
	
	public Collection<String> getAgeIds()
	{
		return ImmutableList.copyOf(ageIds);
	}
	
	public Age getStartingAge()
	{
		return ageMap.get(ageIds.get(0));
	}
	
	public Age getCurrentAge()
	{
		return currentAge;
	}
	
	private void saveState()
	{
		JsonObject json = new JsonObject();
		json.addProperty("age", currentAge.id());
		
		try (Writer writer = Files.newBufferedWriter(STATE_JSON))
		{
			GSON.toJson(json, writer);
		}
		catch (IOException e)
		{
			Ages.LOGGER.error("Error saving file: state.json", e);
		}
	}
	
	private void broadcastAgeAdvancement(MinecraftServer server)
	{
		TitleS2CPacket titlePacket = new TitleS2CPacket(
			currentAge.displayName().copy().styled(
				style -> style.withUnderline(true).withFont(Identifier.ofVanilla("ten"))));
		
		SubtitleS2CPacket subtitlePacket = new SubtitleS2CPacket(UNLOCKED_TEXT);
		
		for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList())
		{
			player.networkHandler.sendPacket(titlePacket);
			player.networkHandler.sendPacket(subtitlePacket);
			player.playSoundToPlayer(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 1.0f, 1.0f);
		}
	}
	
	private <T> Optional<Age> getFutureUnlockAge(T t, Function<Age, Set<T>> supplier)
	{
		return getFutureUnlockAge(age -> supplier.apply(age).contains(t));
	}
	
	private static boolean loadJsonFile(Path path, JsonReaderConsumer consumer)
	{
		try (JsonReader reader = new JsonReader(Files.newBufferedReader(path)))
		{
			consumer.accept(reader);
			return true;
		}
		catch (Exception e)
		{
			Ages.LOGGER.error("Error loading file: {}", path, e);
			return false;
		}
	}
	
	@FunctionalInterface
	private interface JsonReaderConsumer
	{
		void accept(JsonReader reader) throws Exception;
	}
}
