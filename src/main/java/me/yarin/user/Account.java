package me.yarin.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(schema = "accounts")
public class Account {
    // Het Account object
    private String accountName, password;

    public Account(final String accountName, final String password) {
        this.accountName = accountName;
        this.password = password;
    }

    public Account() {

    }

    public String getPassword() {
        return password;
    }

    public String getAccountName() {
        return accountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountName.equals(account.accountName) && password.equals(account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, password);
    }

    @Override
    public String toString() {
        return "Account Name: " +  accountName +
                " Password: " + password + "\n";
    }
}
