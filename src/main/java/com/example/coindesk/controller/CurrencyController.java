package com.example.coindesk.controller;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService service;

    public CurrencyController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Currency> listAll() {
        return service.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> getOne(@PathVariable String code) {
        return service.findByCode(code.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    
    @PostMapping
    public ResponseEntity<Currency> create(@RequestBody Currency currency) {
        Currency saved = service.createOrUpdate(currency);
        return ResponseEntity.created(URI.create("/api/currencies/" + saved.getCode())).body(saved);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Currency> update(@PathVariable String code, @RequestBody Currency currency) {
        return service.findByCode(code.toUpperCase()).map(existing -> {
            existing.setNameZh(currency.getNameZh());
            Currency saved = service.createOrUpdate(existing);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.deleteByCode(code.toUpperCase());
        return ResponseEntity.noContent().build();
    }
}