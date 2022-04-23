package id.test.mekar_investama_sampoerna.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TransactionResponse {
    private Long id;

    private UserBalanceResponse sender;

    private UserBalanceResponse receiver;

    private String type;

    private Integer balance;

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private  Date updatedAt;

    public TransactionResponse() {
    }

    public TransactionResponse(Long id, UserBalanceResponse sender, UserBalanceResponse receiver, String type, Integer balance, Date createdAt, Date updatedAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.balance = balance;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBalanceResponse getSender() {
        return sender;
    }

    public void setSender(UserBalanceResponse sender) {
        this.sender = sender;
    }

    public UserBalanceResponse getReceiver() {
        return receiver;
    }

    public void setReceiver(UserBalanceResponse receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
