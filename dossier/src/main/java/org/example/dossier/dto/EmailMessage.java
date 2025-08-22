package org.example.dossier.dto;

import lombok.*;
import org.example.dossier.dto.enums.Theme;

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
