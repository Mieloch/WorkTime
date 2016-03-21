package topworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import topworker.service.UserService;
import topworker.utils.LoginEncrypter;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginEncrypter loginEncrypter;

    @RequestMapping(value = "/user/active", method = RequestMethod.GET)
    public HttpStatus activeAccount(@RequestParam String login) {
        try {
            String userLogin = loginEncrypter.decrypt(login);
            userService.activateUser(userLogin);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }
}
