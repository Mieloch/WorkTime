package topworker.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import topworker.service.UserService;
import topworker.service.impl.EncryptionServiceImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Properties;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Resource(name = "apiProperties")
    private Properties apiProperties;

    private String ACTIVATION_SUCCESS_URL;
    private String ACTIVATION_FAIL_URL;
    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionServiceImpl encryptionService;

    @PostConstruct
    public void init() {
        ACTIVATION_SUCCESS_URL = apiProperties.getProperty("activate_succes_url");
        ACTIVATION_FAIL_URL = apiProperties.getProperty("activate_fail_url");
    }

    @RequestMapping(value = "/user/active", method = RequestMethod.GET)
    public HttpStatus activeAccount(@RequestParam String login, HttpServletResponse response) {
        try {
            System.out.println(login);
            String s1 = URLDecoder.decode(login, "UTF-8");
            System.out.println(s1);
            String userLogin = encryptionService.decrypt(URLDecoder.decode(login, "UTF-8"));
            System.out.println(userLogin);
            userService.activateUser(userLogin);
            response.sendRedirect(ACTIVATION_SUCCESS_URL);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect(ACTIVATION_FAIL_URL);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }
}
