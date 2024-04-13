package dev.nilkoush.worldguardregionevents;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import dev.nilkoush.worldguardregionevents.events.RegionEnterEvent;
import dev.nilkoush.worldguardregionevents.events.RegionEnteredEvent;
import dev.nilkoush.worldguardregionevents.events.RegionLeaveEvent;
import dev.nilkoush.worldguardregionevents.events.RegionLeftEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.*;

public class Listeners implements Listener {

    private final Map<Player, Set<ProtectedRegion>> playerRegions = new HashMap<Player, Set<ProtectedRegion>>();

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        handleLeaveAndLeft(event);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleLeaveAndLeft(event);
    }

    private void handleLeaveAndLeft(PlayerEvent event) {
        Set<ProtectedRegion> regions = playerRegions.remove(event.getPlayer());
        if (regions != null) {
            for (ProtectedRegion region : regions) {
                RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, event.getPlayer(), MovementWay.DISCONNECT, event);
                RegionLeftEvent leftEvent = new RegionLeftEvent(region, event.getPlayer(), MovementWay.DISCONNECT, event);
                Bukkit.getPluginManager().callEvent(leaveEvent);
                Bukkit.getPluginManager().callEvent(leftEvent);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        event.setCancelled(updateRegions(event.getPlayer(), MovementWay.MOVE, event.getTo(), event));
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        event.setCancelled(updateRegions(event.getPlayer(), MovementWay.TELEPORT, event.getTo(), event));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updateRegions(event.getPlayer(), MovementWay.SPAWN, event.getPlayer().getLocation(), event);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        updateRegions(event.getPlayer(), MovementWay.SPAWN, event.getRespawnLocation(), event);
    }

    private synchronized boolean updateRegions(Player player, MovementWay movement, Location to, PlayerEvent event) {
        Set<ProtectedRegion> regions;
        if (playerRegions.get(player) == null) {
            regions = new HashSet<>();
        }
        else {
            regions = new HashSet<>(playerRegions.get(player));
        }
        Set<ProtectedRegion> oldRegions = new HashSet<>(regions);
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(to.getWorld()));
        if (rm == null) {
            return false;
        }
        HashSet<ProtectedRegion> appRegions = new HashSet<>(rm.getApplicableRegions(BukkitAdapter.asBlockVector(to)).getRegions());
        ProtectedRegion globalRegion = rm.getRegion("__global__");
        if (globalRegion != null) {
            appRegions.add(globalRegion);
        }
        for (ProtectedRegion region : appRegions) {
            if (!regions.contains(region)) {
                RegionEnterEvent regionEnterEvent = new RegionEnterEvent(region, player, movement, event);
                Bukkit.getPluginManager().callEvent(regionEnterEvent);
                if (regionEnterEvent.isCancelled()) {
                    regions.clear();
                    regions.addAll(oldRegions);
                    return true;
                }
                Bukkit.getScheduler().runTaskLater(WorldGuardRegionEvents.getPlugin(), () -> {
                    RegionEnteredEvent regionEnteredEvent = new RegionEnteredEvent(region, player, movement, event);
                    Bukkit.getPluginManager().callEvent(regionEnteredEvent);
                }, 1L);
                regions.add(region);
            }
        }
        Iterator<ProtectedRegion> itr = regions.iterator();
        while (itr.hasNext()) {
            ProtectedRegion region2 = itr.next();
            if (!appRegions.contains(region2)) {
                if (rm.getRegion(region2.getId()) != region2) {
                    itr.remove();
                }
                else {
                    RegionLeaveEvent regionLeaveEvent = new RegionLeaveEvent(region2, player, movement, event);
                    Bukkit.getPluginManager().callEvent(regionLeaveEvent);
                    if (regionLeaveEvent.isCancelled()) {
                        regions.clear();
                        regions.addAll(oldRegions);
                        return true;
                    }
                    Bukkit.getScheduler().runTaskLater(WorldGuardRegionEvents.getPlugin(), () -> {
                        RegionLeftEvent regionLeftEvent = new RegionLeftEvent(region2, player, movement, event);
                        Bukkit.getPluginManager().callEvent(regionLeftEvent);
                    }, 1L);
                    itr.remove();
                }
            }
        }
        playerRegions.put(player, regions);
        return false;
    }
}
