package me.yarin.control;

import me.yarin.user.Account;
import me.yarin.user.AccountRowMapper;
import me.yarin.user.User;
import me.yarin.user.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * De Rest Service, hier worden alle back-end database calls gemaakt.
 */
@Service
public class RestUserService {

    private final List<User> cachedUsers = new ArrayList<>();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUsers() {
        return cachedUsers;
    }

    /**
     * Een methode voor het cachen van alle gebruikers wanneer deze app wordt opgestart.
     * Zo moeten we alleen maar gaan kijken of een gebruiker bestaat door in de [cachedUsers] list te kijken.
     * In de plaats van elke keer een database call te maken
     */
    @PostConstruct
    private void cacheUsers() {
        final List<User> cached = jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
        cached.forEach(user -> user.setAccounts(getUserAccounts(user)));
        cachedUsers.addAll(cached);
    }

    /**
     * Methode voor het aanmaken van een User in de database
     * @param user De user die we gaan toevoegen
     */
    public void createUser(final User user) {
        this.cachedUsers.add(user);
        final String query = "INSERT INTO users (email, password) VALUES (?, ?)";
        jdbcTemplate.update(query, user.getEmail(), user.getPassword());
    }

    /**
     * Methode voor het opvragen van alle accounts van een [user]
     * @param user De user wie zen accounts we gaan opvragen uit de database
     * @return alle accounts van de gebruikers
     */
    public Set<Account> getUserAccounts(final User user) {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM accounts WHERE accountOwner = '"  + user.getEmail() + "'", new AccountRowMapper()));
    }

    /**
     * Een account toevoegen aan een [user]
     * @param user de User waar we een account aan gaan toevoegen
     * @param account het account dat we gaan toevoegen
     */
    public void addAccountEntry(final User user, final Account account) {
        user.addAccount(account);
        final String query = "INSERT INTO accounts (accountOwner, accountName, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, user.getEmail(), account.getAccountName(), account.getPassword());
    }

    /**
     * Een methode voor het kijken of een gebruiker bestaast
     * @param user kijken of deze user bestaat
     * @return true als de user bestaat
     */
    public boolean doesUserExist(final User user) {
        return this.cachedUsers.contains(user);
    }

    /**
     * Methode voor het opvragen van een user bij de user ID
     * @param id het ID van de user
     * @return de User als hij bestaat
     * @throws NullPointerException als de user niet bestaats
     */
    public User getUserByID(final int id) {
        return this.cachedUsers.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    /**
     * Methode voor het opvragen van een User object bij het wachtwoord en email
     * @param email het email
     * @param password het wachtwoord
     * @return een User object die matched met het wachtwoord/email van de user
     * @throws NullPointerException als er geen user met die email en wachtwoord bestaat
     */
    public User getUserByData(final String email, final String password) {
        return this.cachedUsers.stream().filter(user -> user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)).findFirst().orElse(null);
    }

    /**
     * Een methode voor het kijken of een user kan inloggen (valid credentials)
     * @param email het email waar de user mee probeert in te loggen
     * @param password het wachtwoord waar de user mee probeerd in te loggen
     * @return true als de credentials matchen, false als dat niet het geval is
     */
    public boolean canLogIn(final String email, final String password) {
        return this.cachedUsers.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password));
    }

}
