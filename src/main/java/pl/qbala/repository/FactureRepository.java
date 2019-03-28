package pl.qbala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbala.entity.Company;
import pl.qbala.entity.Facture;

import java.util.List;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    Facture findFirstByOrderByDeadline();
    List<Facture> findAllByNumberLike(String number);
    List<Facture> findAllByContractor(Company company);
}
