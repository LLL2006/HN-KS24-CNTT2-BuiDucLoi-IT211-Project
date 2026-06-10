package com.re.it211project.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;

    private String companyName;

    private String address;

    private String website;

    private String description;
}