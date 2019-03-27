package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.repository.FactureRepository;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.FactureRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class FactureController {

    @Autowired
    FactureRepository factureRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/factureForm")
    public String facture(Model model){
        model.addAttribute("facture", new Facture());
        return "factureForm";
    }

    @PostMapping("/factureForm")
    public String facture(@ModelAttribute Facture facture, HttpServletRequest request){
        String taxQualification = request.getParameter("taxQualification");
        facture.setTaxQualification(Integer.parseInt(taxQualification));
        factureRepository.save(facture);
        return "redirect:" + request.getContextPath() + "/factureList";
    }

    @GetMapping("/factureList")
    public String list(Model model){
        List<Facture> allFactures = factureRepository.findAll();
        model.addAttribute("factures", allFactures);
        return "factureList";
    }

    @GetMapping("/taxBook")
    public String taxBook(Model model){
        List<Facture> allFactures = factureRepository.findAll();
        model.addAttribute("allFactures", allFactures);
        return "taxBook";
    }

    @ModelAttribute("companies")
    public List<Company> companies(){
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

}
