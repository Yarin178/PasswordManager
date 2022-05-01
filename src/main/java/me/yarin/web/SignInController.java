package me.yarin.web;

import me.yarin.control.RestUserController;
import me.yarin.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * De controller die het inloggen behandeld.
 */
@Controller
public class SignInController {

    @Autowired
    private RestUserController restUserController;

    /**
     * sign in pagina
     */
    @RequestMapping("sign_in")
    public String signInPage() {
        return "sign_in";
    }

    /**
     * De actie die wordt uitgevoerd om een user proberen in te loggen.
     */
    @RequestMapping("action_signin")
    public String signInUser(final HttpServletRequest request, final Model model) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        final User user = restUserController.getUserService().getUserByData(email, password);
        if (restUserController.logUserIn(user)) {
            model.addAttribute("user", user);
            model.addAttribute("accounts", user.getAccounts());
            return "accountpage";
        } else {
            return signInPage();
        }

    }

}
