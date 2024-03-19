package com.gegentan.paper_plugin;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
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

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Enabled Carry 1.0! (This is my first plugin!)");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity clickedEntity = event.getRightClicked();
        if (player.getPassengers().isEmpty() && player.isSneaking()) {
            player.addPassenger(clickedEntity);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking()) {
            List<Entity> passengers = player.getPassengers();
            if (!passengers.isEmpty()) {
                Entity passenger = passengers.get(0);
                RayTraceResult result = player.rayTraceBlocks(5);

                if (result != null && result.getHitBlock() != null) {
                    Location location = result.getHitBlock().getLocation();
                    location.add(new Vector(0, 1, 0));
                    passenger.leaveVehicle();
                    passenger.teleport(location);

                }
            }
        }
    }

}
