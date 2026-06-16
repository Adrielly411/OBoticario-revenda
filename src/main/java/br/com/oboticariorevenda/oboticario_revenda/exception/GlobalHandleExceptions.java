package br.com.oboticariorevenda.oboticario_revenda.exception;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleExceptions {
    @ExceptionHandler(Exception.class)
    public String handleGeneralExceptions(Exception ex, Model model, Authentication authentication) {
        model.addAttribute("errorMessage", "Desculpe, ocorreu um erro interno no sistema");
        model.addAttribute("openErrorModal", true);

        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getName())) {
            
            boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            if (isAdmin) {
                return "admin/index"; 
            }
        }

        return "index";
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFoundException(ProductNotFoundException ex, Model model, Authentication authentication) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("openErrorModal", true);

        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getName())) {
            
            boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            
            if (isAdmin) {
                return "admin/index"; 
            }
        }

        return "index";
    }

    @ExceptionHandler(ImageKitUploadException.class)
    public String handleImageKitUploadException(ImageKitUploadException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("openErrorModal", true);
        return "admin/criar-produto";
    }
}
