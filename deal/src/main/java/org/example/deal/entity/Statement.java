package org.example.deal.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.example.deal.dto.*;
import org.example.deal.dto.enums.*;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "statement")
public class Statement {

    @Id
    @Column(name = "statement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "sign_data")
    private LocalDateTime signData;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "applied_offer")
    private LoanOfferDto  loanOffer;

    @Column(name = "ses_code")
    private String sesCode;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "status_history")
    private List<StatementStatusHistoryDto> statusHistory;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

}
