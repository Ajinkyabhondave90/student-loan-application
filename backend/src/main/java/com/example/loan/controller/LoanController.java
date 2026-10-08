package com.example.loan.controller;

import com.example.loan.dto.LoanRequest;
import com.example.loan.dto.StatusUpdateRequest;
import com.example.loan.model.Loan;
import com.example.loan.model.LoanStatus;
import com.example.loan.service.LoanService;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService service;

    public LoanController(LoanService service) { this.service = service; }

    @GetMapping
    public List<Loan> list(@RequestParam(required = false) LoanStatus status) {
        return service.findAll(status);
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() { return service.stats(); }

    @GetMapping("/{id}")
    public Loan get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan create(@Valid @RequestBody LoanRequest request) { return service.create(request); }

    @PutMapping("/{id}")
    public Loan update(@PathVariable Long id, @Valid @RequestBody LoanRequest request) {
        return service.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public Loan updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return service.updateStatus(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
