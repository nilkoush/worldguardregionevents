package dev.nilkoush.worldguardregionevents.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.nilkoush.worldguardregionevents.MovementWay;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a region event
 */
public class RegionEvent extends PlayerEvent {

    private static final HandlerList handlerList = new HandlerList();
    private final ProtectedRegion region;
    private final MovementWay movement;
    public PlayerEvent parentEvent;

    public RegionEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
        super(player);
        this.region = region;
        this.movement = movement;
        this.parentEvent = parent;
    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public MovementWay getMovement() {
        return movement;
    }

    public @NotNull HandlerList getHandlers() {
        return RegionEvent.handlerList;
    }

    public static HandlerList getHandlerList() {
        return RegionEvent.handlerList;
    }
}
