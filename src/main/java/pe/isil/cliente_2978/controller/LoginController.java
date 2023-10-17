package pe.isil.cliente_2978.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import pe.isil.cliente_2978.model.User;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static final String LOGIN_API = "http://localhost:8090/api_2978/login/auth"; //POST
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("user", new User());
        return "login/index"; //la vista html
    }
    /*
    @PostMapping("")
    public String login(Model model, User user, RedirectAttributes ra){
        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_API, user, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            ra.addFlashAttribute("msgExito", "Login exitoso");
            System.out.println("Login Exitoso");
        } else {
            ra.addFlashAttribute("msgError", "Error en el inicio de sesión");
            System.out.println("no exito");
        }
        return "login/index";
    }
    */
    @PostMapping("")
    public String login(Model model, User user, RedirectAttributes ra) {
        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_API, user, String.class);

        HttpStatus statusCode = response.getStatusCode();

        if (statusCode.is2xxSuccessful()) {
            ra.addFlashAttribute("msgSuccess", "Login successful");
            model.addAttribute("email", user.getEmail());
            System.out.println("Login Exitoso");


            return "redirect:/login/welcome";
        } else if (statusCode == HttpStatus.UNAUTHORIZED) {
            ra.addFlashAttribute("msgError", "Invalid credentials");
            return "redirect:/login/unauthorized-page";
        } else if (statusCode == HttpStatus.NOT_FOUND) {
            ra.addFlashAttribute("msgError", "User not found");
            return "redirect:/login/not-found-page";
        } else {
            ra.addFlashAttribute("msgError", "Internal server error");
            return "redirect:/login/error-page";
        }
    }


    @GetMapping("/welcome")
    public String welcome(Model model) {
        String email = (String) model.getAttribute("email");
        //model.addAttribute("email", email);
        return "login/welcome";
    }
    @GetMapping("/unauthorized-page")
    public String unauthorized(Model model) {
        return "login/unauthorized-page";
    }
    @GetMapping("/not-found-page")
    public String notFound(Model model) {
        return "login/not-found-page";
    }
    @GetMapping("/error-page")
    public String error(Model model) {
        return "login/error-page";
    }

    @RequestMapping("/error")
    public String handleError(Model model) {
        // Add any necessary error-related data to the model
        return "login/error-page"; // Replace with the appropriate HTML page for error handling
    }



}

