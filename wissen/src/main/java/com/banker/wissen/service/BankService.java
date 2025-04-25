package com.banker.wissen.service;

import com.banker.wissen.entity.BankEntity;
import com.banker.wissen.repository.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {
    BankRepository bankRepository;
    BankService(BankRepository bankRepository){
        this.bankRepository=bankRepository;
    }
    public List<BankEntity> getAllBanks() {
        return bankRepository.findAll();
    }

    public Optional<BankEntity> getBankById(long id) {
        return bankRepository.findById(id);
    }

    public BankEntity createBank(BankEntity bank) {
        return bankRepository.save(bank);
    }

    public BankEntity updateBank(long id, BankEntity updatedBank) {
        return bankRepository.findById(id)
                .map( bank -> {
                    bank.setCustomerName(updatedBank.getCustomerName());
                    bank.setAccountNumber(updatedBank.getAccountNumber());
                    bank.setBalance(updatedBank.getBalance());
                    return bankRepository.save(bank);
                })
                .orElseThrow(() -> new RuntimeException("Bank not found"));
    }

    public boolean deleteBank(long id) {
        if(bankRepository.findById(id).isPresent()){
            bankRepository.deleteById(id);
            return true; }
        else
            return false;
    }
}
