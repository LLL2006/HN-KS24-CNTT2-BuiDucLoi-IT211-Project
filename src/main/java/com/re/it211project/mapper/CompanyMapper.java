package com.re.it211project.mapper;

import com.re.it211project.dto.response.CompanyResponse;
import com.re.it211project.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .address(company.getAddress())
                .website(company.getWebsite())
                .description(company.getDescription())
                .build();
    }
}