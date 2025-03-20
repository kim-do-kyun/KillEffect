package org.desp.killEffect.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.desp.killEffect.dto.PlayerDataDto;

public class PlayerDataRepository {

    private static PlayerDataRepository instance;
    private final MongoCollection<Document> playerDataDB;
    public Map<String, PlayerDataDto> playerDataCache = new HashMap<>();

    public PlayerDataRepository() {
        DatabaseRegister database = new DatabaseRegister();
        this.playerDataDB = database.getDatabase().getCollection("PlayerData");
    }

    public static PlayerDataRepository getInstance() {
        if (instance == null) {
            instance = new PlayerDataRepository();
        }
        return instance;
    }

    public void loadPlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        String user_id = player.getName();

        Document document = new Document("uuid", uuid);
        if (playerDataDB.find(Filters.eq("uuid", uuid)).first() == null) {
            List<String> effectInventory = new ArrayList<>();
            Document newUserDocument= new Document()
                    .append("user_id", user_id)
                    .append("uuid", uuid)
                    .append("effectInventory", effectInventory)
                    .append("equippedEffect", "");
            playerDataDB.insertOne(document);
        }

        String equippedEffect = playerDataDB.find(document).first().getString("equippedEffect");
        List<String> effectInventory = playerDataDB.find(document).first().getList("effectInventory", String.class);

        PlayerDataDto playerDto = PlayerDataDto.builder()
                .user_id(user_id)
                .uuid(uuid)
                .effectInventory(effectInventory)
                .equippedEffect(equippedEffect)
                .build();

        playerDataCache.put(uuid, playerDto);
    }

    public PlayerDataDto getPlayerData(Player player) {
        if (!player.isOnline()) {
            return null;
        }
        return playerDataCache.get(player.getUniqueId().toString());
    }

    public void addKillEffect(Player player, String killEffect) {
        String uuid = player.getUniqueId().toString();
        PlayerDataDto playerDataDto = playerDataCache.get(uuid);
        playerDataDto.getEffectInventory().add(killEffect);
        playerDataCache.put(uuid, playerDataDto);
    }

    public void savePlayerData(Player player) {
        String uuid = player.getUniqueId().toString();
        PlayerDataDto playerDataDto = playerDataCache.get(uuid);

        Document document = new Document()
                .append("user_id", playerDataDto.getUser_id())
                .append("uuid", uuid)
                .append("effectInventory", playerDataDto.getEffectInventory())
                .append("equippedEffect", playerDataDto.getEquippedEffect());

        playerDataDB.replaceOne(
                Filters.eq("uuid", uuid),
                document,
                new ReplaceOptions().upsert(true)
        );
    }
}
