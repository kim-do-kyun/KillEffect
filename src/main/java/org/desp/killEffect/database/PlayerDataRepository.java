package org.desp.killEffect.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bson.Document;

public class PlayerDataRepository {

    private static PlayerDataRepository instance;
    private final MongoCollection<Document> playerDataDB;
    @Getter
    public Map<String, ItemDataDto> itemDataList = new HashMap<>();

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

    public void insertItemData(ItemDataDto newItemData) {
        Document document = new Document()
                .append("MMOItem_ID", newItemData.getMMOItem_ID())
                .append("amount", newItemData.getAmount())
                .append("price", newItemData.getPrice())
                .append("userMaxPurchaseAmount", newItemData.getUserMaxPurchaseAmount())
                .append("serverMaxPurchaseAmount", newItemData.getServerMaxPurchaseAmount())
                .append("slot", newItemData.getSlot());

        playerDataDB.insertOne(document);
    }

    public void loadItemData() {
        FindIterable<Document> documents = playerDataDB.find();
        for (Document document : documents) {
            ItemDataDto item = ItemDataDto.builder()
                    .MMOItem_ID(document.getString("MMOItem_ID"))
                    .amount(document.getInteger("amount"))
                    .price(document.getInteger("price"))
                    .userMaxPurchaseAmount(document.getInteger("userMaxPurchaseAmount"))
                    .serverMaxPurchaseAmount(document.getInteger("serverMaxPurchaseAmount"))
                    .slot(document.getInteger("slot"))
                    .build();

            itemDataList.put(item.getMMOItem_ID(), item);
        }
    }

}
