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
        kafkaProducerService.createDocuments(statementId);
        return ResponseEntity.ok().build();}

    @PostMapping("/{statementId}/sign")
    public ResponseEntity<Void> sign(@PathVariable Long statementId) {
        kafkaProducerService.sendDocuments(statementId);
        return ResponseEntity.ok().build();}

    @PostMapping("/{statementId}/code")
    public ResponseEntity<Void> code(@PathVariable Long statementId,
                                     @RequestParam(name = "code") String code) {
        kafkaProducerService.sendSes(statementId, code);
        return ResponseEntity.ok().build();}
}
