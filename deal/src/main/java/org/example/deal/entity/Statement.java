package org.example.deal.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.example.deal.dto.LoanOfferDto;
import org.example.deal.dto.enums.ApplicationStatus;
import org.example.deal.entity.pojo.StatusHistory;
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

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "applied_offer")
    private List<LoanOfferDto>  loanOffers;

    //я не знаю какого типа должен быть этот параметр
    //private ses_code

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "status_history")
    private List<StatusHistory> statusHistory;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

}
