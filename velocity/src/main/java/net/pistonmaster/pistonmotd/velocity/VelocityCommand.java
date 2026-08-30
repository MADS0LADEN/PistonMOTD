package net.pistonmaster.pistonmotd.velocity;

import com.velocitypowered.api.command.SimpleCommand;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands;
import net.pistonmaster.pistonmotd.shared.PistonMOTDCommands.Outcome;
import net.pistonmaster.pistonmotd.shared.PistonMOTDPlugin;

import java.util.List;

@RequiredArgsConstructor
public class VelocityCommand implements SimpleCommand {
  private final PistonMOTDPlugin plugin;

  @Override
  public void execute(Invocation invocation) {
    Outcome outcome = PistonMOTDCommands.execute(
      plugin,
      invocation.arguments(),
      invocation.source().hasPermission(PistonMOTDCommands.PERM_HELP),
      invocation.source().hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );

    switch (outcome) {
      case HELP:
        invocation.source().sendMessage(Identity.nil(), Component.text("Commands:"));
        invocation.source().sendMessage(Identity.nil(), Component.text("/pistonmotd help"));
        invocation.source().sendMessage(Identity.nil(), Component.text("/pistonmotd reload"));
        break;
      case RELOADED:
        invocation.source().sendMessage(Identity.nil(), Component.text("Reloaded the config!"));
        break;
      case RELOAD_FAILED:
        invocation.source().sendMessage(Identity.nil(), Component.text("Failed to reload the config. Check the console for errors."));
        break;
      case NO_PERMISSION:
        invocation.source().sendMessage(Identity.nil(), Component.text("You don't have permission to do that!"));
        break;
      case UNKNOWN:
        break;
      default:
        break;
    }
  }

  @Override
  public List<String> suggest(Invocation invocation) {
    return PistonMOTDCommands.tabComplete(
      invocation.arguments(),
      invocation.source().hasPermission(PistonMOTDCommands.PERM_HELP),
      invocation.source().hasPermission(PistonMOTDCommands.PERM_RELOAD)
    );
  }

  @Override
  public boolean hasPermission(Invocation invocation) {
    return invocation.source().hasPermission(PistonMOTDCommands.PERM_RELOAD)
      || invocation.source().hasPermission(PistonMOTDCommands.PERM_HELP);
  }
}
