package org.desp.killEffect;

import java.util.Collection;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.desp.killEffect.command.KillEffectInventoryCommand;
import org.desp.killEffect.database.PlayerDataRepository;
import org.desp.killEffect.listener.InventoryClickListener;
import org.desp.killEffect.listener.ItemUseListener;
import org.desp.killEffect.listener.PlayerJoinAndQuitListener;

public final class KillEffect extends JavaPlugin {

    @Getter
    private static KillEffect instance;

    @Override
    public void onEnable() {
        instance = this;

        PlayerDataRepository.getInstance();
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinAndQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemUseListener(), this);
        getCommand("처치효과").setExecutor(new KillEffectInventoryCommand());

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            PlayerDataRepository.getInstance().loadPlayerData(player);
        }
    }

    @Override
    public void onDisable() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            PlayerDataRepository.getInstance().savePlayerData(player);
        }
    }
}
