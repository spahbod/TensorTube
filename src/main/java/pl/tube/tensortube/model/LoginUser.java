package pl.tube.tensortube.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "LoginUser")
@Data
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String userId;
    @Column
    private String pass;
    @Column
    private String firstName;
    @Column
    private String lastName;

    public LoginUser() {}

    public LoginUser(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public String getPass() { return pass; }

    public void setPass(String pass) { this.pass = pass; }
}
