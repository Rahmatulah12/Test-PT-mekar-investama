package id.test.mekar_investama_sampoerna.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_balances",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "balance_no")
    }
)
public class UserBalance extends BaseEntity{

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance_no", length = 20, nullable = false, unique = true)
    private String balanceNo;

    @Column(name="balance", nullable = true)
    private Integer balance;

    @JoinColumn(name="user_id")
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public UserBalance() {
    }

    public UserBalance(String balanceNo, User user) {
        this.balanceNo = balanceNo;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBalanceNo() {
        return balanceNo;
    }

    public void setBalanceNo(String balanceNo) {
        this.balanceNo = balanceNo;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        setBalance(0);
    }
}
