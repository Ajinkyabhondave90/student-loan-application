package com.example.loan.dto;

import java.math.BigDecimal;
import javax.validation.constraints.*;

public class LoanRequest {

    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "University is required")
    private String university;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "10000", message = "Minimum loan amount is 10,000")
    private BigDecimal loanAmount;

    @NotNull(message = "Tenure is required")
    @Min(value = 6, message = "Minimum tenure is 6 months")
    @Max(value = 240, message = "Maximum tenure is 240 months")
    private Integer tenureMonths;

    @NotNull(message = "Annual income is required")
    @DecimalMin(value = "0", message = "Income cannot be negative")
    private BigDecimal annualIncome;

    private String purpose;

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
}
