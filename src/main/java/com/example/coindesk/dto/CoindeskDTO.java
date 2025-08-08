package com.example.coindesk.dto;

import java.util.Map;

public class CoindeskDTO {
    private String timeUpdated;
    private Time  time;
    private String disclaimer;
    private Map<String, Map<String, Object>> bpi; // keep flexible

    public CoindeskDTO() {}

    public String getTimeUpdated() { return timeUpdated; }
    public void setTimeUpdated(String timeUpdated) { this.timeUpdated = timeUpdated; }
    public String getDisclaimer() { return disclaimer; }
    public void setDisclaimer(String disclaimer) { this.disclaimer = disclaimer; }
    public Map<String, Map<String, Object>> getBpi() { return bpi; }
    public void setBpi(Map<String, Map<String, Object>> bpi) { this.bpi = bpi; }
    
    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }
    
    
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updateduk;

        public String getUpdated() { return updated; }
        public void setUpdated(String updated) { this.updated = updated; }

        public String getUpdatedISO() { return updatedISO; }
        public void setUpdatedISO(String updatedISO) { this.updatedISO = updatedISO; }

        public String getUpdateduk() { return updateduk; }
        public void setUpdateduk(String updateduk) { this.updateduk = updateduk; }
    }
    
}