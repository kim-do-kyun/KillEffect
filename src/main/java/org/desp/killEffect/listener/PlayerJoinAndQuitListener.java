package org.desp.killEffect.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.desp.killEffect.KillEffect;
import org.desp.killEffect.database.PlayerDataRepository;

public class PlayerJoinAndQuitListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(KillEffect.getInstance(), new Runnable() {
            @Override
            public void run() {
                PlayerDataRepository.getInstance().loadPlayerData(event.getPlayer());
            }
        }, 20L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerDataRepository.getInstance().savePlayerData(player);
    }
}
