package org.example.deal.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.example.deal.dto.EmploymentDto;
import org.example.deal.dto.enums.Gender;
import org.example.deal.dto.enums.MaritalStatus;
import org.example.deal.entity.pojo.Passport;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String email;

    @Enumerated(EnumType.STRING)
    private Gender getter;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Passport  passport;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private EmploymentDto employment;

    @Column(name = "account_number")
    private String accountNumber;
}
