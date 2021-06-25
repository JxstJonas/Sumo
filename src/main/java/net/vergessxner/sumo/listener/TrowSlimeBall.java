package net.vergessxner.sumo.listener;

import net.vergessxner.sumo.Sumo;
import net.vergessxner.sumo.utils.helpers.KnockBack;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Jonas H.
 * Created: 25 Juni 2021
 */

public class TrowSlimeBall implements Listener {

    //Contains as Key SlimeBall UUID and as Value Player UUID
    private static final ConcurrentHashMap<Item, UUID> balls = new ConcurrentHashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getItem() == null) return;

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§8» §2Slimeball") &&
                (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {

            Location spawnLoc = player.getEyeLocation().toVector().toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
            Item ball = player.getWorld().dropItem(spawnLoc, new ItemStack(Material.SLIME_BALL));
            ball.setVelocity(player.getEyeLocation().getDirection().multiply(2));
            balls.put(ball, player.getUniqueId());
            player.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 0.3f, 0);
        }
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!balls.isEmpty()) {
                    //Remove Item
                    Set<Item> list = balls.keySet();
                    for (Item item : list) {
                        if(item.isOnGround()) {
                            Bukkit.getScheduler().runTask(Sumo.getInstance(), item::remove);
                            balls.remove(item);
                        }
                    }

                    //Hit Player
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(onlinePlayer.getGameMode() == GameMode.SPECTATOR) continue;
                        //Check if a Ball is in this Distance
                        Bukkit.getScheduler().runTask(Sumo.getInstance(), () -> {
                            for (Entity nearbyEntity : onlinePlayer.getNearbyEntities(1.0D, 2.0D, 1.0D)) {
                                if(nearbyEntity instanceof Item) {
                                    Item item = (Item) nearbyEntity;
                                    if(item.getItemStack().getType() == Material.SLIME_BALL) {
                                        UUID uuid = balls.get(item);
                                        if(uuid != null && onlinePlayer.getUniqueId() != uuid) {
                                            Bukkit.getScheduler().runTask(Sumo.getInstance(), item::remove);

                                            //Give Knock-back
                                            //If the Knock-Back is to high make the resistance higher
                                            KnockBack.applyKnockback(onlinePlayer, item, 0.7);
                                        }
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }.runTaskTimerAsynchronously(Sumo.getInstance(), 0, 0);
    }



}
