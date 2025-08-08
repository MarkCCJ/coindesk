package com.example.coindesk.service;

import com.example.coindesk.dto.CoindeskDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoindeskService {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 取得並解析 coindesk JSON（參考 https://kengp3.github.io/blog/coindesk.json）
     * 回傳簡化的 Map: { "USD": { "rate_float": 12345.67, "description": "United States Dollar" }, ... }
     */
    public Map<String, Object> fetchAndTransform(String url) {
        CoindeskDTO dto = restTemplate.getForObject(url, CoindeskDTO.class);
        Map<String, Object> out = new HashMap<>();
        if (dto == null) return out;

        out.put("timeUpdated", dto.getTime() != null ? dto.getTime().getUpdated() : null);
        out.put("disclaimer", dto.getDisclaimer());

        Map<String, Map<String, Object>> bpi = dto.getBpi();
        Map<String, Map<String, Object>> simplified = new HashMap<>();
        if (bpi != null) {
            for (Map.Entry<String, Map<String, Object>> e : bpi.entrySet()) {
                String code = e.getKey();
                Map<String, Object> inner = e.getValue();
                Map<String, Object> small = new HashMap<>();
                // 常見欄位：rate, rate_float, description, code
                if (inner.containsKey("rate")) small.put("rate", inner.get("rate"));
                if (inner.containsKey("rate_float")) small.put("rate_float", inner.get("rate_float"));
                if (inner.containsKey("description")) small.put("description", inner.get("description"));
                simplified.put(code, small);
            }
        }
        out.put("bpi", simplified);
        return out;
    }
}