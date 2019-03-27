package pl.qbala.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbala.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findOneByEmail(String email);
}
