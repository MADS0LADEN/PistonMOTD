package net.pistonmaster.pistonmotd.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands.Outcome;
import net.pistonmaster.pistonmotd.shared.PistonMOTDPlugin;

import java.util.List;

public class BungeeCommand extends Command implements TabExecutor {
  private final PistonMOTDPlugin plugin;

  protected BungeeCommand(PistonMOTDPlugin plugin) {
    super("pistonmotd", null, "pistonmotdb", "pistonmotdbungee", "pmotd");
    this.plugin = plugin;
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    Outcome outcome = PistonMOTDCommands.execute(
      plugin,
      args,
      sender.hasPermission(PistonMOTDCommands.PERM_HELP),
      sender.hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );

    switch (outcome) {
      case HELP:
        sender.sendMessage(new ComponentBuilder("Commands:").create());
        sender.sendMessage(new ComponentBuilder("/pistonmotd help").create());
        sender.sendMessage(new ComponentBuilder("/pistonmotd reload").create());
        break;
      case RELOADED:
        sender.sendMessage(new ComponentBuilder("Reloaded the config!").create());
        break;
      case RELOAD_FAILED:
        sender.sendMessage(new ComponentBuilder("Failed to reload the config. Check the console for errors.").create());
        break;
      case NO_PERMISSION:
        sender.sendMessage(new ComponentBuilder("You don't have permission to do that!").create());
        break;
      case UNKNOWN:
        break;
      default:
        break;
    }
  }

  @Override
  public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
    return PistonMOTDCommands.tabComplete(
      args,
      sender.hasPermission(PistonMOTDCommands.PERM_HELP),
      sender.hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );
  }
}
