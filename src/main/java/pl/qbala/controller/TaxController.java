package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.qbala.entity.Facture;
import pl.qbala.repository.FactureRepository;

import java.util.List;

@Controller
public class TaxController {

    @Autowired
    FactureRepository factureRepository;

    @GetMapping("/taxes")
    public String taxes(Model model){
        List<Facture> all = factureRepository.findAll();
        float cost = 0;
        float income = 0;
        float vatToPay = 0;
        float vatToGet = 0;

        for (Facture facture : all) {
            if(facture.getContractor().getType().equals("Klient")){
                income += facture.getPriceNet()*facture.getQuantity();
                vatToPay += ((float)facture.getVat()/100)*facture.getPriceNet()*facture.getQuantity();
            }else {
                cost += facture.getPriceNet()*facture.getQuantity();
                vatToGet += ((float)facture.getVat()/100)*facture.getPriceNet()*facture.getQuantity();
            }
        }

        model.addAttribute("income", income);
        model.addAttribute("cost", cost);
        model.addAttribute("vatToPay", vatToPay);
        model.addAttribute("vatToGet", vatToGet);
        model.addAttribute("factures", all);
        return "taxes";
    }

}
