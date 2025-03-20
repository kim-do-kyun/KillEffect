package org.desp.killEffect.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.desp.killEffect.database.PlayerDataRepository;
import org.desp.killEffect.dto.PlayerDataDto;
import org.jetbrains.annotations.NotNull;

public class EffectInventoryGUI implements InventoryHolder {

    private final Inventory inventory = Bukkit.createInventory(this, 54, "처치 효과 인벤토리");

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public EffectInventoryGUI(Player player) {
        setUpInventory(this.inventory, player);
    }

    public void setUpInventory(Inventory inventory, Player player) {
        PlayerDataDto playerData = PlayerDataRepository.getInstance().getPlayerData(player);
        List<String> effectList = playerData.getEffectInventory();
        int slot = 0;
        // 사망이펙트 아이템 값
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
//        itemMeta.setCustomModelData(10489);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7 - 클릭 시 해당 처치 효과를 장착합니다.");
        lore.add("");
        itemMeta.setLore(lore);

        // 그냥 ui 꾸미는 아이템
        ItemStack uiBlank = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta uiBlankItemMeta = uiBlank.getItemMeta();
        uiBlankItemMeta.setDisplayName("§f");
        uiBlank.setItemMeta(uiBlankItemMeta);
        // 칭호 해제 버튼
        ItemStack unSetKillEffect = new ItemStack(Material.BARRIER);
        ItemMeta unSetKillEffectItemMeta = unSetKillEffect.getItemMeta();
        unSetKillEffectItemMeta.setDisplayName("§c 사망효과 해제하기");
        unSetKillEffect.setItemMeta(unSetKillEffectItemMeta);

        for(int i = 45; i<54;i++){
            if(i==49){
                inventory.setItem(i, unSetKillEffect);
                continue;
            }
            inventory.setItem(i, uiBlank);
        }
        for (String effect : effectList) {
            if(slot > 45){
                break;
            }
            itemMeta.setDisplayName("§f"+effect);
            itemStack.setItemMeta(itemMeta);
            this.inventory.setItem(slot, itemStack);
            slot++;
        }
    }
}
