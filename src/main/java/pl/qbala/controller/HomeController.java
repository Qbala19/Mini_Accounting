package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.service.CompanyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyService companyService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("company", new Company());
        return "registerForm";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Company company, HttpServletRequest request) {
        companyService.save(company);
        return "redirect:" + request.getContextPath() + "/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("company", new Company());
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Company company, HttpSession session, HttpServletRequest request) {
        try {
            Company logedInCompany = companyService.checkLogin(company.getEmail(), company.getPassword());
            session.setAttribute("company", logedInCompany);
            return "redirect:" + request.getContextPath() + "/";
        }catch (Exception e){
            return "loginForm";
        }
    }


}
