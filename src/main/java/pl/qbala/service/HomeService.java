package pl.qbala.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qbala.entity.Facture;
import pl.qbala.repository.FactureRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    FactureRepository factureRepository;

    public Map<String, Long> getDashboardPaymentDays(){
        Map<String,Long> mapOfDeadlines = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate zusDeadline = LocalDate.of(now.getYear(), now.getMonth(), 15);
        LocalDate pitDeadline = LocalDate.of(now.getYear(), now.getMonth(), 20);
        LocalDate vatDeadline = LocalDate.of(now.getYear(), now.getMonth(), 25);

        mapOfDeadlines.put("zus", zusDeadline.toEpochDay()- LocalDate.now().toEpochDay());
        mapOfDeadlines.put("pit", pitDeadline.toEpochDay() - LocalDate.now().toEpochDay());
        mapOfDeadlines.put("vat", vatDeadline.toEpochDay() - LocalDate.now().toEpochDay());

        return mapOfDeadlines;
    }

}
