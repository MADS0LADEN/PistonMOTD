package net.pistonmaster.pistonmotd.shared;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class PistonMOTDPluginLoadConfigTest {
  @Test
  void firstLoadCreatesConfigAndReturnsTrue(@TempDir Path tempDir) {
    PistonMOTDPlugin plugin = new PistonMOTDPlugin(new TestPistonMOTDPlatform(tempDir));

    Assertions.assertTrue(plugin.loadConfig());
    Assertions.assertTrue(Files.exists(tempDir.resolve("config.yml")));
    Assertions.assertTrue(plugin.getPluginConfig().isDescriptionActivated());
  }

  @Test
  void reloadUpdatesInMemoryConfig(@TempDir Path tempDir) throws Exception {
    PistonMOTDPlugin plugin = new PistonMOTDPlugin(new TestPistonMOTDPlatform(tempDir));
    Assertions.assertTrue(plugin.loadConfig());

    Path configFile = tempDir.resolve("config.yml");
    String yaml = Files.readString(configFile);
    String updatedYaml = yaml.replaceFirst("activated: true", "activated: false");
    Files.writeString(configFile, updatedYaml);

    Assertions.assertTrue(plugin.loadConfig());
    Assertions.assertFalse(plugin.getPluginConfig().isDescriptionActivated());
  }
}

class PistonMOTDCommandsTest {
  @Test
  void executeReloadWithPermissionReturnsReloaded(@TempDir Path tempDir) {
    CountingPistonMOTDPlugin plugin = new CountingPistonMOTDPlugin(new TestPistonMOTDPlatform(tempDir));

    PistonMOTDCommands.Outcome outcome = PistonMOTDCommands.execute(
      plugin,
      new String[] {PistonMOTDCommands.RELOAD},
      false,
      true
    );

    Assertions.assertEquals(PistonMOTDCommands.Outcome.RELOADED, outcome);
    Assertions.assertEquals(1, plugin.getLoadConfigCalls());
  }

  @Test
  void executeReloadWithoutPermissionReturnsNoPermission(@TempDir Path tempDir) {
    CountingPistonMOTDPlugin plugin = new CountingPistonMOTDPlugin(new TestPistonMOTDPlatform(tempDir));
    plugin.loadConfig();

    PistonMOTDCommands.Outcome outcome = PistonMOTDCommands.execute(
      plugin,
      new String[] {PistonMOTDCommands.RELOAD},
      false,
      false
    );

    Assertions.assertEquals(PistonMOTDCommands.Outcome.NO_PERMISSION, outcome);
    Assertions.assertEquals(1, plugin.getLoadConfigCalls());
  }

  @Test
  void tabCompletePrefixMatches() {
    List<String> completions = PistonMOTDCommands.tabComplete(new String[] {"re"}, true, true);
    Assertions.assertEquals(List.of("reload"), completions);

    List<String> helpCompletions = PistonMOTDCommands.tabComplete(new String[] {"h"}, true, false);
    Assertions.assertEquals(List.of("help"), helpCompletions);
  }

  @Test
  void tabCompleteRespectsPermissions() {
    Assertions.assertEquals(List.of(), PistonMOTDCommands.tabComplete(new String[] {"h"}, false, false));
    Assertions.assertEquals(List.of("reload"), PistonMOTDCommands.tabComplete(new String[] {"r"}, false, true));
  }

  private static final class CountingPistonMOTDPlugin extends PistonMOTDPlugin {
    private final AtomicInteger loadConfigCalls = new AtomicInteger();

    private CountingPistonMOTDPlugin(PistonMOTDPlatform platform) {
      super(platform);
    }

    int getLoadConfigCalls() {
      return loadConfigCalls.get();
    }

    @Override
    public boolean loadConfig() {
      loadConfigCalls.incrementAndGet();
      return super.loadConfig();
    }
  }
}
