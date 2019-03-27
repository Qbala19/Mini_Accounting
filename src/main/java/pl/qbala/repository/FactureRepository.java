package pl.qbala.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbala.entity.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
