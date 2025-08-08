package com.example.coindesk.service;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepository repo;

    public CurrencyService(CurrencyRepository repo) {
        this.repo = repo;
    }

    public List<Currency> findAll() { return repo.findAll(); }

    public Optional<Currency> findByCode(String code) { return repo.findById(code); }

    public Currency createOrUpdate(Currency c) { return repo.save(c); }

    public void deleteByCode(String code) { repo.deleteById(code); }
}