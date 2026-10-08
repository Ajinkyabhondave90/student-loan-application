package com.example.loan.service;

import com.example.loan.dto.LoanRequest;
import com.example.loan.dto.StatusUpdateRequest;
import com.example.loan.exception.NotFoundException;
import com.example.loan.model.Loan;
import com.example.loan.model.LoanStatus;
import com.example.loan.repository.LoanRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanService {

    private final LoanRepository repository;
    private final BigDecimal interestRate;

    public LoanService(LoanRepository repository, @Value("${loan.interest-rate}") BigDecimal interestRate) {
        this.repository = repository;
        this.interestRate = interestRate;
    }

    @Transactional(readOnly = true)
    public List<Loan> findAll(LoanStatus status) {
        return status == null
                ? repository.findAllByOrderByCreatedAtDesc()
                : repository.findByStatusOrderByCreatedAtDesc(status);
    }

    @Transactional(readOnly = true)
    public Loan findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Application " + id + " not found"));
    }

    public Loan create(LoanRequest req) {
        Loan loan = new Loan();
        apply(loan, req);
        return repository.save(loan);
    }

    public Loan update(Long id, LoanRequest req) {
        Loan loan = findById(id);
        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new IllegalStateException("Only pending applications can be edited");
        }
        apply(loan, req);
        return repository.save(loan);
    }

    public Loan updateStatus(Long id, StatusUpdateRequest req) {
        Loan loan = findById(id);
        loan.setStatus(req.getStatus());
        loan.setRemarks(req.getRemarks());
        return repository.save(loan);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Transactional(readOnly = true)
    public Map<String, Object> stats() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("total", repository.count());
        m.put("pending", repository.countByStatus(LoanStatus.PENDING));
        m.put("approved", repository.countByStatus(LoanStatus.APPROVED));
        m.put("rejected", repository.countByStatus(LoanStatus.REJECTED));
        m.put("approvedAmount", repository.sumAmountByStatus(LoanStatus.APPROVED));
        return m;
    }

    private void apply(Loan loan, LoanRequest r) {
        loan.setStudentName(r.getStudentName().trim());
        loan.setEmail(r.getEmail().trim());
        loan.setPhone(r.getPhone());
        loan.setUniversity(r.getUniversity().trim());
        loan.setCourse(r.getCourse().trim());
        loan.setLoanAmount(r.getLoanAmount());
        loan.setTenureMonths(r.getTenureMonths());
        loan.setAnnualIncome(r.getAnnualIncome());
        loan.setPurpose(r.getPurpose());
        loan.setInterestRate(interestRate);
        loan.setEmi(calculateEmi(r.getLoanAmount(), interestRate, r.getTenureMonths()));
    }

    /** EMI = P * r * (1+r)^n / ((1+r)^n - 1), where r is the monthly rate. */
    static BigDecimal calculateEmi(BigDecimal principal, BigDecimal annualRate, int months) {
        double r = annualRate.doubleValue() / 12 / 100;
        double p = principal.doubleValue();
        double emi = r == 0 ? p / months : p * r * Math.pow(1 + r, months) / (Math.pow(1 + r, months) - 1);
        return BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP);
    }
}
