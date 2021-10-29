package io.mogagi.quant.alert;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertMsg {
    private String title;
    private String content;
}
