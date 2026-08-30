package net.pistonmaster.pistonmotd.sponge;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands;
import net.pistonmaster.pistonmotd.shared.PistonMOTDPlugin;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;

@RequiredArgsConstructor
public class SpongeReloadCommand implements CommandExecutor {
  private final PistonMOTDPlugin plugin;

  @Override
  public CommandResult execute(CommandContext context) {
    if (!context.subject().hasPermission(PistonMOTDCommands.PERM_RELOAD)) {
      return CommandResult.error(Component.text("You don't have permission to do that!"));
    }

    if (plugin.loadConfig()) {
      context.sendMessage(Identity.nil(), Component.text("Reloaded the config!"));
      return CommandResult.success();
    }

    return CommandResult.error(Component.text("Failed to reload the config. Check the console for errors."));
  }
}
