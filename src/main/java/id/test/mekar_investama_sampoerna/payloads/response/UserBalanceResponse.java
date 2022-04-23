package id.test.mekar_investama_sampoerna.payloads.response;

public class UserBalanceResponse {

    private Long id;

    private String balanceNo;

    private UserResponse user;

    public UserBalanceResponse() {
    }

    public UserBalanceResponse(Long id, String balanceNo, UserResponse user) {
        this.id = id;
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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
