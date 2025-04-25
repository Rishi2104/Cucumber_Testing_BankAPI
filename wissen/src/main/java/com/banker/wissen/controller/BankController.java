package com.banker.wissen.controller;

import com.banker.wissen.entity.BankEntity;
import com.banker.wissen.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/banking")
public class BankController {
    BankService bankService;
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    @GetMapping
    public List<BankEntity> getAllBanks() {
        return bankService.getAllBanks();
    }

    @GetMapping("/{id}")
    public BankEntity getBankById(@PathVariable long id) {
        return bankService.getBankById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account with given account number does not exist. Sorry!")
                );
    }

    @PostMapping("/insert")
    public BankEntity createBank(@RequestBody BankEntity bank) {
        return bankService.createBank(bank);
    }

    @PutMapping("/{id}")
    public BankEntity updateBank(@PathVariable long id, @RequestBody BankEntity bank) {
        return bankService.updateBank(id, bank);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBank(@PathVariable long id) {
        if(bankService.deleteBank(id)){
            return ResponseEntity.ok("Deleted !");
        }
        return ResponseEntity.ok("Not Found ! ");
    }
}
