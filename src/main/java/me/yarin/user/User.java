package me.yarin.user;

import org.springframework.data.annotation.AccessType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AccessType(AccessType.Type.FIELD)
@Entity
@Table(name = "users")
public class User {
    // Het User object
    private String email;
    private String password;
    private Set<Account> accounts;
    @Id
    private int id;

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
        this.accounts = new HashSet<>();
    }

    public User(final String email, final String password, final int id) {
        this(email, password);
        this.id = id;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(final Account account) {
        this.accounts.add(account);
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public void removeAccount(final Account account) {
        this.accounts.remove(account);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "Email " + email + " Password " + password + "\n";
    }
}
