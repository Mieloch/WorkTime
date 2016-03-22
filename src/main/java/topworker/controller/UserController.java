package topworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import topworker.service.UserService;
import topworker.service.impl.EncryptionServiceImpl;

import java.net.URLDecoder;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionServiceImpl encryptionService;

    @RequestMapping(value = "/user/active", method = RequestMethod.GET)
    public HttpStatus activeAccount(@RequestParam String login) {
        try {
            System.out.println(login);
            String s1 = URLDecoder.decode(login, "UTF-8");
            System.out.println(s1);
            String userLogin = encryptionService.decrypt(URLDecoder.decode(login, "UTF-8"));
            System.out.println(userLogin);
            userService.activateUser(userLogin);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }
}
