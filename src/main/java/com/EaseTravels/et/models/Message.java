package com.EaseTravels.et.models;

import com.EaseTravels.et.models.types.MessageColors;
import com.EaseTravels.et.models.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String content;
    @Builder.Default
    private String type = MessageType.INFO.toString();
    @Builder.Default
    private String color = MessageColors.blue.toString();
}
