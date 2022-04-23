package id.test.mekar_investama_sampoerna.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.test.mekar_investama_sampoerna.entities.Role;
import id.test.mekar_investama_sampoerna.entities.User;
import lombok.Data;
import java.util.Set;

@Data
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    @JsonProperty("phone_no")
    private String phoneNo;

    private Set<Role> roles;

    public UserResponse(Long id, String username, String email, String phoneNo, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.roles = roles;
    }

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phoneNo = user.getPhoneNo();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
