package com.example.demo.dto;

public class TransactionDTO {

    private Long id;
    private Double amount;
    private String referenceId;
    private Long sourceAccountId;
    private Long targetAccountId;
    private String status;

    public TransactionDTO() {
    }

    public TransactionDTO(Long id, Double amount, String referenceId, Long sourceAccountId, Long targetAccountId, String status) {
        this.id = id;
        this.amount = amount;
        this.referenceId = referenceId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(Long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
