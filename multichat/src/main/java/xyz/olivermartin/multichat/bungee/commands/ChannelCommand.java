package xyz.olivermartin.multichat.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.olivermartin.multichat.bungee.ChatModeManager;
import xyz.olivermartin.multichat.bungee.ConfigManager;
import xyz.olivermartin.multichat.bungee.MessageManager;

/**
 * Chat Channel Command
 * <p>Players can use this command to switch channels, as well as show and hide specific channels</p>
 * 
 * @author Oliver Martin (Revilo410)
 *
 */
public class ChannelCommand extends Command {

	public ChannelCommand() {
		super("channel", "multichat.chat.channel", (String[]) ConfigManager.getInstance().getHandler("config.yml").getConfig().getStringList("channelcommand").toArray(new String[0]));
	}

	private void showHelp(CommandSender sender) {

		MessageManager.sendMessage(sender, "command_channel_help");

	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if ((sender instanceof ProxiedPlayer)) {

			if ((args.length < 1) || ((args.length == 1) && (args[0].toLowerCase().equals("help")))) {

				showHelp(sender);

			} else if (args.length == 1) {

				showHelp(sender);

			} else if (args.length == 2) {

				String subCommand = args[0].toLowerCase();
				String operand = args[1].toLowerCase();

				switch (subCommand) {

				case "switch":
					if (!sender.hasPermission("multichat.chat.channel.switch")) {
						MessageManager.sendMessage(sender, "command_channel_switch_no_permission");
						return;
					}
					if (operand.equals("local")) {
						ChatModeManager.getInstance().setLocal(((ProxiedPlayer)sender).getUniqueId());
						MessageManager.sendSpecialMessage(sender, "command_channel_switch", operand.toUpperCase());
					} else if (operand.equals("global")) {
						ChatModeManager.getInstance().setGlobal(((ProxiedPlayer)sender).getUniqueId());
						MessageManager.sendSpecialMessage(sender, "command_channel_switch", operand.toUpperCase());
					} else {
						MessageManager.sendMessage(sender, "command_channel_does_not_exist");
					}
					break;

				default:
					showHelp(sender);
					break;
				}

			}

		} else {
			MessageManager.sendMessage(sender, "command_channel_only_players");
		}

	}

}