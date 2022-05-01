package me.yarin.web;

import me.yarin.control.RestUserController;
import me.yarin.user.Account;
import me.yarin.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * De controller voor Accounts
 */
@Controller
public class AccountController {

    @Autowired
    private RestUserController restUserController;

    /**
     * Redirect een gebruiker naar de accountpage
     */
    @RequestMapping("accountpage")
    public String accountPage(final Model model, final User user) {
        model.addAttribute("accounts", user.getAccounts());
        return "accountpage";
    }

    /**
     * De actie die we uitvoeren als een gebruiker een nieuw account probeerd toe te voegen.
     */
    @RequestMapping("action_addAccount")
    public String addAccount(final HttpServletRequest request, final Model model) {
        final User user = restUserController.getUserService().getUserByData(request.getParameter("yourEmail"), request.getParameter("yourPassword"));
        final Account account = new Account(request.getParameter("accountName"), request.getParameter("password"));
        restUserController.addAccountToUser(account, Integer.parseInt(request.getParameter("yourID")));
        model.addAttribute("account", account);
        model.addAttribute("user", user);
        return accountPage(model, user);
    }

}
