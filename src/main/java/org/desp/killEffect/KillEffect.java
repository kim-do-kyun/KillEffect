package org.desp.killEffect;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class KillEffect extends JavaPlugin {

    @Getter
    private static KillEffect instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
