package topworker.view.utils;

import com.vaadin.navigator.ViewChangeListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import topworker.view.naviagtion.information.ActivateFail;
import topworker.view.naviagtion.information.ActivateSuccess;
import topworker.view.naviagtion.information.Home;
import topworker.view.naviagtion.login.Login;
import topworker.view.naviagtion.signup.SignUp;

/**
 * Created by echomil on 06.03.16.
 */

public class ViewChangeSecurityChecker implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        String[] notAuthViews = new String[]{Login.VIEW_NAME, Home.VIEW_NAME, SignUp.VIEW_NAME, ActivateFail.VIEW_NAME, ActivateSuccess.VIEW_NAME};
        for (String s : notAuthViews) {
            if (event.getViewName().equals(s)) {
                return true;
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        if (authentication.getPrincipal().equals("anonymousUser")) {
            authentication.setAuthenticated(false);
        }
        return authentication == null ? false : authentication.isAuthenticated();
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

    }
}