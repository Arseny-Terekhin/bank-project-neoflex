package org.example.deal.dto;

import lombok.*;
import org.example.deal.dto.enums.Theme;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailMessage {

    private Long statementId;
    private String address;
    private String text;
    private Theme theme;
}
