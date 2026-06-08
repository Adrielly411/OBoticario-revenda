package br.com.oboticariorevenda.oboticario_revenda.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String getAdminIndex() {
        return "/admin/index";
    }

    @GetMapping("/admin/relatorio")
    public String getAdminReport() {
        return "/admin/report";
    }

    @GetMapping("/admin/criar-produto")
    public String getAdminCreateProduct() {
        return "/admin/create-product";
    }

    @GetMapping("/admin/editar-produto")
    public String getAdminEditProduct() {
        return "/admin/edit-product";
    }
}
