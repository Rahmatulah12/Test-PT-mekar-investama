package id.test.mekar_investama_sampoerna.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "transactions")
public class Transaction extends  BaseEntity implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "sender_id", unique = false)
    @ManyToOne(targetEntity = UserBalance.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserBalance sender;

    @JoinColumn(name = "receiver_id", unique = false)
    @ManyToOne(targetEntity = UserBalance.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserBalance receiver;

    @Column(name="type", nullable = false, length = 25)
    private String type;

    @Column(nullable = true)
    private Integer balance;

    public Transaction() {
    }

    public Transaction(Long id, UserBalance sender, UserBalance receiver, String type, Integer balance) {
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

    public UserBalance getSender() {
        return sender;
    }

    public void setSender(UserBalance sender) {
        this.sender = sender;
    }

    public UserBalance getReceiver() {
        return receiver;
    }

    public void setReceiver(UserBalance receiver) {
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
