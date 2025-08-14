package org.example.deal.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.deal.entity.Statement;
import org.example.deal.repository.StatementRepository;
import org.example.deal.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImplAdminService implements AdminService {

    private final StatementRepository statementRepository;

    @Override
    public List<Statement> findAllStatement() {
        return statementRepository.findAll();
    }

    @Override
    public Statement findStatement(Long statementId) {
        Statement statement = statementRepository.findById(statementId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Statement not found");
                });
        return statement;
    }
}
