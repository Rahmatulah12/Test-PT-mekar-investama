package id.test.mekar_investama_sampoerna.payloads.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

public class SignUpRequest {
    @NotBlank
    @Size(min=6, max=50, message = "username, must at least 6 to 50 characters")
    private String username;

    @NotBlank
    @Email
    @Size(max=75, message = "Email max 75 characters length")
    private String email;

    @JsonProperty("phone_no")
    @Size(min=10, max=20, message = "phone no, must at least 10 to 20 characters")
    @Pattern(regexp = "(^[0-9]+$|^$)", message = "phone no is only number")
    private String phoneNo;

    @NotBlank
    @Size(min=8)
    private String password;

    private Set<String> role;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String email, String phoneNo, String password) {
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
