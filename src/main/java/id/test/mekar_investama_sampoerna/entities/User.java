package id.test.mekar_investama_sampoerna.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone_no")
        })

public class User extends BaseEntity implements Serializable {
        public static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 50)
        @Column(nullable = false, unique = true, length=50)
        private String username;

        @NotBlank
        @Size(max = 75)
        @Email
        @Column(nullable = false, unique = true, length=75)
        private String email;

        @NotBlank
        @Size(max = 20)
        @Column(name= "phone_no", nullable = false, unique = true, length=20)
        private String phoneNo;

        @NotBlank
        @Size(max = 255)
        @Column(nullable = false, length=255)
        private String password;

        @ManyToMany(fetch=FetchType.LAZY)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
        private Set<Role> roles = new HashSet<>();

        public User() {
        }

        public User(String username, String email, String phoneNo, String password) {
                this.username = username;
                this.email = email;
                this.phoneNo = phoneNo;
                this.password = password;
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

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }
}
