package io.Daro.project.security;


import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
class SsoController {

    @GetMapping("/logout")
    void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }
}
