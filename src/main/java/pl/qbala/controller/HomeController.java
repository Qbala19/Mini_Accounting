package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.service.CompanyService;
import pl.qbala.service.HomeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    CompanyService companyService;
    @Autowired
    HomeService homeService;

    @GetMapping("/")
    public String home(Model model) {
        Month month = LocalDate.now().getMonth();
        model.addAttribute("month", month);
        Map<String, Integer> dashboardPaymentDays = homeService.getDashboardPaymentDays();
        model.addAttribute("zusDays", dashboardPaymentDays.get("zus"));
        model.addAttribute("pitDays", dashboardPaymentDays.get("pit"));
        model.addAttribute("vatDays", dashboardPaymentDays.get("vat"));
        model.addAttribute("nearestFacture", homeService.getNearestFacture());
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

    @GetMapping("/firmDetails")
    public String firmDetails(Model model){
        List<Company> contractors = companyRepository.findAll();
        model.addAttribute("contractors", contractors);
        List<Company> clients = companyRepository.findAllByType("Klient");
        List<Company> suppliers = companyRepository.findAllByType("Dostawca");
        float incomes = 0;
        float costs = 0;
        for (Company client: clients) {
            for (Facture facture: client.getFactures()) {
                incomes += facture.getPriceNet()*facture.getQuantity();
            }
        }
        for (Company supplier: suppliers) {
            for (Facture facture: supplier.getFactures()) {
                costs += facture.getPriceNet()*facture.getQuantity();
            }
        }
        model.addAttribute("incomes", incomes);
        model.addAttribute("costs", costs);
        model.addAttribute("clients", clients);
        model.addAttribute("suppliers",suppliers);
        return "firmDetails";
    }

}
