package pl.qbala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qbala.entity.Facture;
import pl.qbala.repository.FactureRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    FactureRepository factureRepository;

    public Map<String, Integer> getDashboardPaymentDays(){
        Map<String,Integer> mapOfDeadlines = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate zusDeadline = LocalDate.of(now.getYear(), now.getMonth(), 15);
        LocalDate pitDeadline = LocalDate.of(now.getYear(), now.getMonth(), 20);
        LocalDate vatDeadline = LocalDate.of(now.getYear(), now.getMonth(), 25);

        mapOfDeadlines.put("zus", zusDeadline.compareTo(now));
        mapOfDeadlines.put("pit", pitDeadline.compareTo(now));
        mapOfDeadlines.put("vat", vatDeadline.compareTo(now));

        return mapOfDeadlines;
    }

    public int getNearestFacture(){
        Facture facture = factureRepository.findFirstByOrderByDeadline();
        LocalDate deadline = facture.getDeadline();
        return deadline.compareTo(LocalDate.now());
    }

}
