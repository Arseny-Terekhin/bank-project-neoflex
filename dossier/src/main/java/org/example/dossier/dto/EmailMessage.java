package org.example.dossier.dto;

import org.example.dossier.dto.enums.Theme;
import lombok.*;

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
