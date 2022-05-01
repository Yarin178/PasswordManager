package me.yarin.web;

import me.yarin.control.RestUserController;
import me.yarin.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * De controller die het sign up process behandeld
 */
@Controller
public class SignUpController {

    @Autowired
    private RestUserController restUserController;

    /**
     * sign up pagina
     */
    @RequestMapping("signup")
    public String signUpPage() {
        return "signup";
    }

    /**
     * De actie die wordt uitegevoerd wanneer een User probeert een accout aan te maken
     */
    @RequestMapping("action_signup")
    public String signUpUser(final HttpServletRequest request, final Model model) {
        final String email = request.getParameter("email");
        final String password = request.getParameter("password");
        final User user = new User(email, password);
        if (restUserController.createUser(user)) {
            model.addAttribute("user", user);
            return "accountpage";
        } else {
            return signUpPage();
        }

    }


}
