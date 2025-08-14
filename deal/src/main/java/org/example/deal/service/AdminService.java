package org.example.deal.service;

import org.example.deal.entity.Statement;

import java.util.List;

public interface AdminService {

    List<Statement> findAllStatement();

    Statement findStatement(Long statementId);
}
