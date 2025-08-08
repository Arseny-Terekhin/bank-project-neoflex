package org.example.deal.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.entity.Statement;
import org.example.deal.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/deal")
@Validated
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService service;

    @GetMapping("/admin/statement")
    public ResponseEntity<List<Statement>> getAllStatement() {
        log.info("Start find all statements");

        List<Statement> statements = service.findAllStatement();

        log.info("End find all statements");
        return ResponseEntity.ok(statements);
    }

    @GetMapping("/admin/statement/{statementId}")
    public ResponseEntity<Statement> getStatement(@PathVariable @Min(value = 1, message = "statementId должен быть больше 0") Long statementId) {
        log.info("Start  find statement, request body: {}",  statementId);

        Statement statement = service.findStatement(statementId);

        log.info("End  find statement, request body: {}",  statementId);
        return ResponseEntity.ok(statement);
    }

}
