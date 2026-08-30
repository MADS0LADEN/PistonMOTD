package net.pistonmaster.pistonmotd.sponge;

import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;

public class SpongeHelpCommand implements CommandExecutor {
  @Override
  public CommandResult execute(CommandContext context) {
    if (!context.subject().hasPermission(PistonMOTDCommands.PERM_HELP)) {
      return CommandResult.error(Component.text("You don't have permission to do that!"));
    }

    context.sendMessage(Identity.nil(), Component.text("Commands:"));
    context.sendMessage(Identity.nil(), Component.text("/pistonmotd help"));
    context.sendMessage(Identity.nil(), Component.text("/pistonmotd reload"));

    return CommandResult.success();
  }
}
