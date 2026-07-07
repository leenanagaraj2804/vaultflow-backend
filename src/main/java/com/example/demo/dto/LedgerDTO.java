package com.example.demo.dto;

import java.util.Date;

public class LedgerDTO {

    private Long id;
    private String description;
    private Date createdAt;

    public LedgerDTO() {
    }

    public LedgerDTO(Long id, String description, Date createdAt) {
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
