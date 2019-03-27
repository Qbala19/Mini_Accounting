package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CompanyController {

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
}
