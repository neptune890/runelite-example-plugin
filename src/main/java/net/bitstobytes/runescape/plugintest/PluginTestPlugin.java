package net.bitstobytes.runescape.plugintest;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "PluginTest"
)
public class PluginTestPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PluginTestConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Example started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Example stopped!");
	}

	//Add event listeners using @Subscribe followed by the event
	//This example watches overhead text messages to change the overhead text to "1000000KC" when we say "!kc Arching"
	@Subscribe
	public void onOverheadTextChanged(OverheadTextChanged e){
		if(e.getActor().equals(client.getLocalPlayer()) && e.getOverheadText().equals("!Kc Arching")){
			client.getLocalPlayer().setOverheadText("1000000KC");
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	@Provides
	PluginTestConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PluginTestConfig.class);
	}
}
