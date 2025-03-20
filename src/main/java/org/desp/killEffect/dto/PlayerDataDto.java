package org.desp.killEffect.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PlayerDataDto {
    private String user_id;
    private String uuid;
    private List<String> effectInventory;
    private String equippedEffect;
}
