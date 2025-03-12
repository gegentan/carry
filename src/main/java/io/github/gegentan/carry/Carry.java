package io.github.gegentan.carry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.List;

public class Carry extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled Carry 1.0!");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();
        if (player.isSneaking() && player.getPassengers().isEmpty()) {
            player.addPassenger(clickedEntity);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) { // STILL GETTING EXECUTED TWICE (SOMETIMES ONCE WHEN HOLDING AN ITEM)!!!!!
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking()) {
            List<Entity> passengers = player.getPassengers();
            if (!passengers.isEmpty()) {
                Entity passenger = passengers.getFirst();
                RayTraceResult result = player.rayTraceBlocks(5);

                if (result != null && result.getHitBlock() != null) {
                    Location location = result.getHitBlock().getLocation();
                    location.add(new Vector(0.5, 1, 0.5));
                    passenger.leaveVehicle();
                    passenger.teleport(location);
                }
                event.setCancelled(true);
            }
        }
    }

}
