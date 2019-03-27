package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.repository.FactureRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CompanyController {

    @Autowired
    FactureRepository factureRepository;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/companyForm")
    public String addNewCompany(Model model){
        Company company = new Company();
        model.addAttribute("company", company);
        return "companyForm";
    }

    @PostMapping("/companyForm")
    public String saveNewCompany(@ModelAttribute Company company, HttpServletRequest request){
        companyRepository.save(company);
        return "redirect:"+request.getContextPath()+"/companyList";
    }

    @GetMapping("/companyList")
    public String companyList(Model model){
        model.addAttribute("companies", companyRepository.findAll());
        return "companyList";
    }

    @GetMapping("/companyEdit")
    public String companyEdition(Model model, @RequestParam(name = "id") Long id){
        Company company = companyRepository.findOne(id);
        model.addAttribute("company",company);
        return "/companyForm";
    }

    @PostMapping("/companyEdit")
    public String companyEdit(@ModelAttribute Company company, HttpServletRequest request){
        companyRepository.save(company);
        return "redirect:" + request.getContextPath() + "/companyList";
    }

    @GetMapping("/companyDelete")
    public String companyDelete(Model model, HttpServletRequest request, @RequestParam(name = "id") Long id){
        Company contractor = companyRepository.findOne(id);
        if(contractor.getFactures().size() == 0){
            companyRepository.delete(id);
        }else{
            model.addAttribute("contractorToDelete", contractor);
            return "companyDeleteConfirm";
        }
        return "redirect:"+request.getContextPath()+"/companyList";
    }

    @GetMapping("/companyDeleteConfirm")
    public String confirm(@RequestParam(name = "id") Long id, HttpServletRequest request){
        companyRepository.findOne(id).getFactures().forEach(f->factureRepository.delete(f));
        companyRepository.delete(id);
        return "redirect:"+request.getContextPath()+"/companyList";
    }
}
