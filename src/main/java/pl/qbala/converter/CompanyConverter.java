package pl.qbala.converter;

import org.springframework.core.convert.converter.Converter;
import pl.qbala.entity.Company;

public class CompanyConverter implements Converter<String, Company> {

    @Override
    public Company convert(String s) {
        return null;
    }
}
