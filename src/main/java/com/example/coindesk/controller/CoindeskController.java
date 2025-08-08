package com.example.coindesk.controller;

import com.example.coindesk.entity.Currency;
import com.example.coindesk.service.CoindeskService;
import com.example.coindesk.service.CurrencyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {

    private final CoindeskService service;
    // default url 
    private static final String DEFAULT_URL = "https://kengp3.github.io/blog/coindesk.json";
    
    @Autowired
    private CurrencyService currencyService; // 這樣就能使用 findByCode


    public CoindeskController(CoindeskService service) {
        this.service = service;
    }

    /**
     * 
     * 
     */
    @GetMapping("/fetch")
    public Map<String, Object> fetch(@RequestParam(value = "url", required = false) String url) {
        String target = (url == null || url.isEmpty()) ? DEFAULT_URL : url;
        return service.fetchAndTransform(target);
    }
    
    @GetMapping("/fetch-transformed")
    public Map<String, Object> fetchTransformed() {
        Map<String, Object> raw = service.fetchAndTransform(DEFAULT_URL);

        if (raw == null) {
            throw new RuntimeException("fetchAndTransform 回傳 null，請檢查 service 實作");
        }

        // 直接從 raw 拿 time 字串
        String rawTime = (String) raw.get("timeUpdated");
        String formattedTime = null;

        if (rawTime != null) {
            try {
                // 原時間格式範例: "Sep 2, 2024 07:07:20 UTC"
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);
                ZonedDateTime zdt = ZonedDateTime.parse(rawTime, inputFormatter);
                formattedTime = zdt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            } catch (Exception e) {
                // 解析失敗就用原字串
                formattedTime = rawTime;
            }
        }

        // 取得 bpi
        Map<String, Map<String, Object>> bpi = (Map<String, Map<String, Object>>) raw.get("bpi");
        List<Map<String, Object>> currencyList = new ArrayList<>();
        if (bpi != null) {
            for (Map.Entry<String, Map<String, Object>> entry : bpi.entrySet()) {
                String code = entry.getKey();
                String nameZh = currencyService.findByCode(code)
                        .map(Currency::getNameZh)
                        .orElse("未知幣別");
                Object rate = entry.getValue().get("rate_float");

                Map<String, Object> item = new HashMap<>();
                item.put("code", code);
                item.put("nameZh", nameZh);
                item.put("rate", rate);
                currencyList.add(item);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("updateTime", formattedTime);
        result.put("currencies", currencyList);
        return result;
    }
}