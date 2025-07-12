package org.example.deal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deal.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deal/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentsController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/{statementId}/send")
    public ResponseEntity<Void> send(@PathVariable Long statementId) {
        log.info("Received request to send documents for statementId={}", statementId);

        kafkaProducerService.createDocuments(statementId);

        log.info("Documents sent to Kafka for statementId={}", statementId);
        return ResponseEntity.ok().build();}

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> sign(@PathVariable Long statementId) {
        log.info("Received request to sign documents for statementId={}", statementId);

        kafkaProducerService.sendDocuments(statementId);

        log.info("Sign request sent to Kafka for statementId={}", statementId);
        return ResponseEntity.ok().build();}

    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> code(@PathVariable Long statementId,
                                     @RequestParam(name = "code") String code) {
        log.info("Received code submission for statementId={}, code={}", statementId, code);

        kafkaProducerService.sendSes(statementId, code);

        log.info("SES code sent to Kafka for statementId={}", statementId);
        return ResponseEntity.ok().build();}
}
