package org.desp.killEffect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.desp.killEffect.gui.EffectInventoryGUI;
import org.jetbrains.annotations.NotNull;

public class KillEffectInventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s,
                             @NotNull String[] strings) {

        Player player = (Player) commandSender;
        EffectInventoryGUI effectInventoryGUI = new EffectInventoryGUI(player);
        player.openInventory(effectInventoryGUI.getInventory());
        return false;
    }
}
