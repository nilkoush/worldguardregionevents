package dev.nilkoush.worldguardregionevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.nilkoush.worldguardregionevents.MovementWay;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player enters a region
 */
public class RegionEnterEvent extends RegionEvent {

    private boolean cancelled;
    private boolean cancellable;

    /**
     * Constructor for RegionEnterEvent
     * @param region The region the player entered
     * @param player The player that entered the region
     * @param movement The way the player entered the region
     * @param parent The parent event
     */
    public RegionEnterEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
        super(region, player, movement, parent);
        this.cancelled = false;
        this.cancellable = movement != MovementWay.SPAWN && movement != MovementWay.DISCONNECT;
    }

    /**
     * Check if the event is cancelled
     * @return True if the event is cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Set the event to cancelled
     * @param cancelled True to cancel the event
     */
    public void setCancelled(boolean cancelled) {
        if (!this.cancellable) {
            return;
        }
        this.cancelled = cancelled;
    }

    /**
     * Check if the event is cancellable
     * @return True if the event is cancellable
     */
    public boolean isCancellable() {
        return cancellable;
    }

    /**
     * Set if the event is cancellable
     * @param cancellable True to make the event cancellable
     */
    public void setCancellable(boolean cancellable) {
        if (!(this.cancellable = cancellable)) {
            this.cancelled = false;
        }
    }
}
