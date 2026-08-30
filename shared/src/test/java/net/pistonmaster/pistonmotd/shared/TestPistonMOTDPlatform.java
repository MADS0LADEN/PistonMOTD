package net.pistonmaster.pistonmotd.shared;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestPistonMOTDPlatform implements PistonMOTDPlatform {
  private final Path pluginDir;

  public TestPistonMOTDPlatform(Path pluginDir) {
    this.pluginDir = pluginDir;
  }

  @Override
  public boolean isPluginEnabled(String pluginName) {
    return false;
  }

  @Override
  public StatusFavicon createFavicon(Path path) {
    return new StatusFavicon(path);
  }

  @Override
  public Path getPluginConfigFile() {
    return pluginDir.resolve("config.yml");
  }

  @Override
  public Path getFaviconFolder() {
    return pluginDir.resolve("favicons");
  }

  @Override
  public List<PlayerWrapper> getPlayers() {
    return List.of();
  }

  @Override
  public int getMaxPlayers() {
    return 0;
  }

  @Override
  public int getPlayerCount() {
    return 0;
  }

  @Override
  public String getVersion() {
    return "test";
  }

  @Override
  public void info(String message) {}

  @Override
  public void warn(String message, Throwable t) {}

  @Override
  public void error(String message, Throwable t) {}

  @Override
  public boolean isSuperVanishBukkitAvailable() {
    return false;
  }

  @Override
  public boolean isPremiumVanishBukkitAvailable() {
    return false;
  }

  @Override
  public boolean isPremiumVanishBungeeAvailable() {
    return false;
  }

  @Override
  public boolean isPremiumVanishVelocityAvailable() {
    return false;
  }

  @Override
  public boolean isLuckPermsAvailable() {
    return false;
  }

  @Override
  public Class<?> getPlayerClass() {
    return Object.class;
  }

  @Override
  public void runAsync(Runnable runnable, long delay, long period, TimeUnit unit) {}
}
