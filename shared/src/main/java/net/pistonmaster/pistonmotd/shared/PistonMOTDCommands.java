package net.pistonmaster.pistonmotd.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class PistonMOTDCommands {
  public static final String HELP = "help";
  public static final String RELOAD = "reload";
  public static final String PERM_HELP = "pistonmotd.help";
  public static final String PERM_RELOAD = "pistonmotd.reload";

  public enum Outcome {
    HELP,
    RELOADED,
    RELOAD_FAILED,
    NO_PERMISSION,
    UNKNOWN
  }

  private PistonMOTDCommands() {}

  public static Outcome execute(PistonMOTDPlugin plugin, String[] args, boolean canHelp, boolean canReload) {
    if (args.length == 0 || HELP.equalsIgnoreCase(args[0])) {
      if (!canHelp) {
        return Outcome.NO_PERMISSION;
      }
      return Outcome.HELP;
    }

    if (RELOAD.equalsIgnoreCase(args[0])) {
      if (!canReload) {
        return Outcome.NO_PERMISSION;
      }
      return plugin.loadConfig() ? Outcome.RELOADED : Outcome.RELOAD_FAILED;
    }

    return Outcome.UNKNOWN;
  }

  public static List<String> tabComplete(String[] args, boolean canHelp, boolean canReload) {
    if (args.length != 1 || args[0] == null) {
      return List.of();
    }

    List<String> commands = new ArrayList<>();
    if (canHelp) {
      commands.add(HELP);
    }
    if (canReload) {
      commands.add(RELOAD);
    }

    String prefix = args[0].toLowerCase(Locale.ROOT);
    List<String> completions = new ArrayList<>();
    for (String command : commands) {
      if (command.toLowerCase(Locale.ROOT).startsWith(prefix)) {
        completions.add(command);
      }
    }

    Collections.sort(completions);
    return completions;
  }
}
