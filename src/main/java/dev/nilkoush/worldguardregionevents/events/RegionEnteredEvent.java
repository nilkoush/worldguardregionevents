package dev.nilkoush.worldguardregionevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.nilkoush.worldguardregionevents.MovementWay;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player has entered a region
 */
public class RegionEnteredEvent extends RegionEvent {

    /**
     * Constructor for RegionEnteredEvent
     * @param region The region the player entered
     * @param player The player that entered the region
     * @param movement The way the player entered the region
     * @param parent The parent event
     */
    public RegionEnteredEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
        super(region, player, movement, parent);
    }
}
