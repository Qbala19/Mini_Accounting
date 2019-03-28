package pl.qbala.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.repository.FactureRepository;
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
    @Autowired
    FactureRepository factureRepository;

    @GetMapping("/")
    public String home(Model model) {
        LocalDate today = LocalDate.now();
        model.addAttribute("today", today);
        Month month = today.getMonth();
        model.addAttribute("month", month);
        Map<String, Long> dashboardPaymentDays = homeService.getDashboardPaymentDays();
        model.addAttribute("zusDays", dashboardPaymentDays.get("zus"));
        model.addAttribute("pitDays", dashboardPaymentDays.get("pit"));
        model.addAttribute("vatDays", dashboardPaymentDays.get("vat"));
        Facture firstDeadline = factureRepository.findFirstByOrderByDeadline();
        model.addAttribute("nearestFacture", firstDeadline);
        model.addAttribute("factureDays", firstDeadline.getDeadline().toEpochDay()-LocalDate.now().toEpochDay());
//        model.addAttribute("nearestFacture", homeService.getNearestFacture().);
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

    @GetMapping("/passwordEdit")
    public String password(){
        return "passwordEdit";
    }


    @PostMapping("/passwordEdit")
    public String passwordEdit(Model model, @RequestParam(name = "oldPassword") String oldPassword,
                               @RequestParam(name = "newPassword") String newPassword,
                               @RequestParam(name = "newPasswordConfirmation") String newPasswordConfirmation){
        Company one = companyRepository.findOne(Long.parseLong("1"));
        try {
            Company company = companyService.checkLogin(one.getEmail(), oldPassword);
            if(newPassword.equals(newPasswordConfirmation)){
                company.setPassword(newPassword);
                companyService.save(company);
            }else {
                model.addAttribute("result","Hasła się różnią!");
                return "/passwordEdit";
            }
        }catch (Exception e){
            model.addAttribute("result",e.getMessage());
            return "/passwordEdit";
        }
        return "/passwordEditInformation";
    }
}
