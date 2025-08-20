package org.example.deal.dto;

import lombok.*;
import org.example.deal.dto.enums.ApplicationStatus;
import org.example.deal.dto.enums.ChangeType;

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
