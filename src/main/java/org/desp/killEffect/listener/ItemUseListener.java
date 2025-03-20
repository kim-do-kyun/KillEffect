package org.desp.killEffect.listener;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.desp.killEffect.database.PlayerDataRepository;

public class ItemUseListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Type type = MMOItems.getType(itemInMainHand);
            if(type == null){
                return;
            }
            if(type.equals(Type.get("KILL_EFFECT"))){
                String id = MMOItems.getID(itemInMainHand).replace("처치효과_", "");
                if(PlayerDataRepository.getInstance().getPlayerData(player).getEffectInventory().contains(id)){
                    player.sendMessage("§c 이미 §f"+id+"§c 처치효과를 가지고 있습니다!");
                    event.setCancelled(true);
                    return;
                }
                itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                PlayerDataRepository.getInstance().addKillEffect(player, id);
//                PlayerDataRepository.getInstance().getPlayerData(player).getEquippedEffect() 로 현재 장착된 처치효과 이름 확인가능

                player.sendMessage("§a 성공적으로 §f"+id+"§a 처치효과를 획득했습니다! §7§o(/처치효과)");
            }
        }
    }
}
