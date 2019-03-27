package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.repository.FactureRepository;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.FactureRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //factureRepository.save(facture);
        Company contractor = companyRepository.findOne(Long.parseLong(request.getParameter("compan")));
        facture.setContractor(contractor);
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

    @GetMapping("/factureEdit")
    public String factureEdition(Model model, @RequestParam(name = "id") Long id){
        Facture facture = factureRepository.findOne(id);
        model.addAttribute("facture",facture);
        return "/factureForm";
    }

    @PostMapping("/factureEdit")
    public String factureEdit(@ModelAttribute Facture facture, HttpServletRequest request){
        factureRepository.save(facture);
        return "redirect:" + request.getContextPath() + "/factureList";
    }

    @GetMapping("/factureDelete")
    public String factureDelete(HttpServletRequest request, @RequestParam(name = "id") Long id){
        factureRepository.delete(id);
        return "redirect:"+request.getContextPath()+"/factureList";
    }

    @GetMapping("/factureDetails")
    public String factureDetails(Model model, @RequestParam(name = "id") Long id){
        model.addAttribute("facture", factureRepository.findOne(id));
        return "factureDetails";
    }


    @ModelAttribute("companies")
    public List<Company> companies(){
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

//    @ModelAttribute("companiesMap")
//    public List<Long> comp(){
//        List<Long> map = new ArrayList<>();
//        List<Company> all = companyRepository.findAll();
//        all.forEach(el->map.add(el.getId()));
//
//        return map;
//    }
}
