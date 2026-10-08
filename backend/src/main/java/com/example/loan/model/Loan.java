package com.example.loan.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String studentName;
    @Column(nullable = false) private String email;
    @Column(nullable = false) private String phone;
    @Column(nullable = false) private String university;
    @Column(nullable = false) private String course;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal loanAmount;
    @Column(nullable = false) private Integer tenureMonths;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal annualIncome;
    private String purpose;

    @Column(precision = 5, scale = 2) private BigDecimal interestRate;
    @Column(precision = 14, scale = 2) private BigDecimal emi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status = LoanStatus.PENDING;

    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String v) { this.studentName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { this.email = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }
    public String getUniversity() { return university; }
    public void setUniversity(String v) { this.university = v; }
    public String getCourse() { return course; }
    public void setCourse(String v) { this.course = v; }
    public BigDecimal getLoanAmount() { return loanAmount; }
    public void setLoanAmount(BigDecimal v) { this.loanAmount = v; }
    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer v) { this.tenureMonths = v; }
    public BigDecimal getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(BigDecimal v) { this.annualIncome = v; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String v) { this.purpose = v; }
    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal v) { this.interestRate = v; }
    public BigDecimal getEmi() { return emi; }
    public void setEmi(BigDecimal v) { this.emi = v; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus v) { this.status = v; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String v) { this.remarks = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
