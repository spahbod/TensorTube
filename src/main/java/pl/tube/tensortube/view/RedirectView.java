package pl.tube.tensortube.view;

import org.springframework.web.context.annotation.ApplicationScope;
import javax.inject.Named;

@Named
@ApplicationScope
public class RedirectView {

    private static final String LOGIN_PAGE_REDIRECT = "/login.xhtml?faces-redirect=true";
    private static final String LOGOUT_PAGE_REDIRECT = "/logout.xhtml?faces-redirect=true";

    public String goToLoginPage() {
        return LOGIN_PAGE_REDIRECT;
    }

    public String goToLogoutPage() {
        return LOGOUT_PAGE_REDIRECT;
    }
}
