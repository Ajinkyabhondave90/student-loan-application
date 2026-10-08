package com.example.loan.repository;

import com.example.loan.model.Loan;
import com.example.loan.model.LoanStatus;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findAllByOrderByCreatedAtDesc();

    List<Loan> findByStatusOrderByCreatedAtDesc(LoanStatus status);

    long countByStatus(LoanStatus status);

    @Query("select coalesce(sum(l.loanAmount), 0) from Loan l where l.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") LoanStatus status);
}
