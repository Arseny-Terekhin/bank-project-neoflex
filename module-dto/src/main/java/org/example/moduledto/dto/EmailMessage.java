package org.example.moduledto.dto;

import lombok.*;
import org.example.moduledto.dto.enums.Theme;

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
