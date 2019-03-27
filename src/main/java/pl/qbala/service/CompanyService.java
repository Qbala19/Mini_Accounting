package pl.qbala.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;
import pl.qbala.entity.Company;
import pl.qbala.repository.CompanyRepository;


@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public void save(Company company) {
        boolean hashPass = false;
        if (company.getId() == null) {
            hashPass = true;
        } else if (company.getPassword() == null || company.getPassword().trim().length() == 0) {
            //check if password not changed
            Company oldCompanyData = companyRepository.findOne(company.getId());
            company.setPassword(oldCompanyData.getPassword());
        } else {
            //password changed
            hashPass = true;
        }

        if (hashPass) {
            String hashedPass = BCrypt.hashpw(company.getPassword(), BCrypt.gensalt());
            company.setPassword(hashedPass);
        }
        companyRepository.save(company);
    }


    public Company checkLogin(String email, String password) throws Exception {
        Company company = companyRepository.findOneByEmail(email);
        if (company == null) {
            throw new Exception("User not found");
        }

        if(BCrypt.checkpw(password, company.getPassword())){
            return company;
        }else {
            throw new Exception("Wrong user or password");
        }
    }

}
