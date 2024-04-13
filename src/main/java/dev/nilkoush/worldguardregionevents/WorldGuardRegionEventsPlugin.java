package dev.nilkoush.worldguardregionevents;

import org.bukkit.plugin.java.JavaPlugin;

public class WorldGuardRegionEventsPlugin extends JavaPlugin {

    public void onEnable() {
        WorldGuardRegionEvents.onEnable(this);
    }
}