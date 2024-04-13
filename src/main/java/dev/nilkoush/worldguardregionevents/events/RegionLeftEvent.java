package dev.nilkoush.worldguardregionevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.nilkoush.worldguardregionevents.MovementWay;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player leaves a region
 */
public class RegionLeftEvent extends RegionEvent {

    /**
     * Constructor for RegionLeftEvent
     * @param region The region the player left
     * @param player The player that left the region
     * @param movement The way the player left the region
     * @param parent The parent event
     */
    public RegionLeftEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
        super(region, player, movement, parent);
    }
}
