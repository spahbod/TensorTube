package pl.tube.tensortube.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tube.tensortube.model.LoginUser;
import pl.tube.tensortube.repository.LoginUserRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class UserManager implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private static final String HOME_PAGE_REDIRECT = "/secured/home.xhtml?faces-redirect=true";
    private static final String LOGOUT_PAGE_REDIRECT = "/logout.xhtml?faces-redirect=true";

    private String userId;
    private String userPassword;

    private LoginUser currentUser;

    @Autowired
    private LoginUserRepository loginUserRepository;

    public String login() {
        // lookup the user based on user name and user password
        currentUser = find(userId, userPassword);

        if (currentUser != null) {
            log.info("login successful for '{}'", userId);

            return HOME_PAGE_REDIRECT;
        } else {
            log.info("login failed for '{}'", userId);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed", "Invalid or unknown credentials."));

            return null;
        }
    }

    public String logout() {
        String identifier = userId;

        // invalidate the session
        log.debug("invalidating session for '{}'", identifier);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        log.info("logout successful for '{}'", identifier);
        return LOGOUT_PAGE_REDIRECT;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    private LoginUser find(String userId, String password) {
        LoginUser result = null;

        String passHash = DigestUtils.sha1Hex(password);
        LoginUser loginUser = loginUserRepository.findByUserIdAndPassOrderById(userId, passHash);

        if (loginUser != null) {
            result = loginUser;
        }
        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public LoginUser getCurrentUser() {
        return currentUser;
    }

    // do not provide a setter for currentUser!
}
