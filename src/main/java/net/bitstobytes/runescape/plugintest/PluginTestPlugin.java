package net.bitstobytes.runescape.plugintest;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import static net.runelite.client.RuneLite.RUNELITE_DIR;
//import net.runelite.http.api.RuneLiteAPI;

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

	private static final File LOG_DIR = new File(RUNELITE_DIR, "Chatlogs");

	private FileWriter myWriter;

	@Override
	protected void startUp() throws Exception {
		log.info("Chatlogs started!");
		LOG_DIR.mkdir();
	}

	@Override
	protected void shutDown() throws Exception {
		log.info("Chatlogs stopped!");
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
	public void onGameStateChanged(GameStateChanged gameStateChanged) {
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN) {
			//client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}

	//Add listener for when there is a chat message..
	@Subscribe
	public void onChatMessage(ChatMessage chatMessage) {
		ChatMessageType chatMessageType = chatMessage.getType();
		// Action to take when receiving new chat message
		// messageQueue.offer(chatMessage.getMessageNode());
		try {
			myWriter = new FileWriter(new File(LOG_DIR, "Chatlogs.txt"), true);
			myWriter.write(chatMessageType.getType() + chatMessage.getTimestamp() + ":" + chatMessage.getName() + ":" + chatMessage.getMessage() + "\r\n");
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}


	@Provides
	PluginTestConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(PluginTestConfig.class);
	}
}
