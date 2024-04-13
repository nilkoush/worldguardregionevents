package dev.nilkoush.worldguardregionevents;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardRegionEvents {

    private static JavaPlugin plugin;

    /**
     * Register the plugin
     *
     * @param plugin The plugin
     */
    public static void onEnable(JavaPlugin plugin) {
        if (WorldGuardRegionEvents.plugin == null) {
            WorldGuardRegionEvents.plugin = plugin;
            if (Bukkit.getPluginManager().getPlugin("WorldGuard") == null) {
                System.out.println("Could not find WorldGuard, disabling.");
                Bukkit.getPluginManager().disablePlugin(plugin);
            }
            Bukkit.getPluginManager().registerEvents(new Listeners(), plugin);
        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}