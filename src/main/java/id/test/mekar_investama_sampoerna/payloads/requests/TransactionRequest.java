package id.test.mekar_investama_sampoerna.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TransactionRequest {
    private Long id;

    @NotBlank
    @Size(min=10, max = 20)
    @JsonProperty("sender")
    private String sender;

    @NotBlank
    @Size(min = 10, max = 20)
    @JsonProperty("receiver")
    private String receiver;

    @NotBlank
    private String type;

    private Integer balance;

    public TransactionRequest() {
    }

    public TransactionRequest(Long id, String sender, String receiver, String type, Integer balance) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
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
}
