package pl.qbala.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbala.entity.Company;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findOneByEmail(String email);

    List<Company> findAllByType(String type);
    List<Company> findAllByNipLike(String nip);
}
