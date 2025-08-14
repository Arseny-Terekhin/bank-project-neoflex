package org.example.moduledto.dto;

import lombok.*;
import org.example.moduledto.dto.enums.ApplicationStatus;
import org.example.moduledto.dto.enums.ChangeType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatementStatusHistoryDto {
    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
