package org.desp.killEffect.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.desp.killEffect.database.PlayerDataRepository;
import org.desp.killEffect.dto.PlayerDataDto;
import org.desp.killEffect.gui.EffectInventoryGUI;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        if(event.getInventory().getHolder() instanceof EffectInventoryGUI){
            Player player = (Player) event.getWhoClicked();
            PlayerDataDto playerData = PlayerDataRepository.getInstance().getPlayerData(player);
            event.setCancelled(true);
            int slot = event.getSlot();
            if(slot == 49){
                playerData.setEquippedEffect("");
                player.sendMessage("§a 성공적으로 처치효과를 해제했습니다!");
            }
            if(slot >= 45){
                return;
            }
            ItemStack item = inventory.getItem(slot);
            String effect = item.getItemMeta().getDisplayName().replace("§f", "");
            playerData.setEquippedEffect(effect);
            player.sendMessage("§a 성공적으로 §f"+effect+"§a 처치효과를 장착했습니다!");
        }
    }
}
