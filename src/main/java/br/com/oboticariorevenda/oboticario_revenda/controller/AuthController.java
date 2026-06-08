package br.com.oboticariorevenda.oboticario_revenda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AuthController {
    @GetMapping("/secret-login")
    public String getSecretLoginPage() {
        return "secret-login";
    }
}
