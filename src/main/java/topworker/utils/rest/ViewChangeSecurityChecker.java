package topworker.utils.rest;

import com.vaadin.navigator.ViewChangeListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import topworker.view.Login;

/**
 * Created by echomil on 06.03.16.
 */
public class ViewChangeSecurityChecker implements ViewChangeListener {

    @Override
    public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event) {
        if (event.getNewView() instanceof Login) {

            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            authentication.setAuthenticated(false);
        }
        return authentication == null ? false : authentication.isAuthenticated();
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

    }
}