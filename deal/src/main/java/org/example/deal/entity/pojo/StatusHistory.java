package org.example.deal.entity.pojo;

import lombok.*;
import org.example.deal.dto.enums.ChangeType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusHistory {
    private String status;
    private LocalDateTime time;
    private ChangeType changeType;
}
