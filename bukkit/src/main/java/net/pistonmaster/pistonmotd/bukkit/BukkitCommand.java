package net.pistonmaster.pistonmotd.bukkit;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands.Outcome;
import net.pistonmaster.pistonmotd.shared.PistonMOTDPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import javax.annotation.Nonnull;
import java.util.List;

@RequiredArgsConstructor
@SuppressWarnings({"deprecation"})
public class BukkitCommand implements CommandExecutor, TabExecutor {
  private final PistonMOTDPlugin plugin;

  @Override
  public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String s, String[] args) {
    String[] effectiveArgs = args;
    if ("pistonmotdreload".equalsIgnoreCase(command.getName())) {
      effectiveArgs = new String[] {PistonMOTDCommands.RELOAD};
    }

    Outcome outcome = PistonMOTDCommands.execute(
      plugin,
      effectiveArgs,
      sender.hasPermission(PistonMOTDCommands.PERM_HELP),
      sender.hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );

    switch (outcome) {
      case HELP:
        sender.spigot().sendMessage(new ComponentBuilder("Commands:").create());
        sender.spigot().sendMessage(new ComponentBuilder("/pistonmotd help").create());
        sender.spigot().sendMessage(new ComponentBuilder("/pistonmotd reload").create());
        return true;
      case RELOADED:
        sender.spigot().sendMessage(new ComponentBuilder("Reloaded the config!").create());
        return true;
      case RELOAD_FAILED:
        sender.spigot().sendMessage(new ComponentBuilder("Failed to reload the config. Check the console for errors.").create());
        return true;
      case NO_PERMISSION:
        sender.spigot().sendMessage(new ComponentBuilder("You don't have permission to do that!").create());
        return true;
      case UNKNOWN:
        return false;
      default:
        return false;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, @Nonnull Command command, @Nonnull String s, @Nonnull String[] args) {
    return PistonMOTDCommands.tabComplete(
      args,
      sender.hasPermission(PistonMOTDCommands.PERM_HELP),
      sender.hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );
  }
}
