package net.bitstobytes.runescape.plugintest;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("PluginTest")
public interface PluginTestConfig extends Config
{
	@ConfigItem(
			keyName = "greeting",
			name = "Welcome Greeting",
			description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "Hello";
	}
	@ConfigItem(
			keyName = "greetingtwo",
			name = "GreetingTwo",
			description = "ATestConfigItem")
	default String greetingtwo()
	{
		return "Hello";
	}
}
