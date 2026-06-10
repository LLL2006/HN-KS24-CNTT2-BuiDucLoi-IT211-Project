package com.re.it211project.dto.response;

import com.re.it211project.enums.JobStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;

    private String title;

    private String description;

    private String location;

    private Double salary;

    private LocalDate deadline;

    private JobStatus status;

    private CompanyResponse company;

    private UserResponse employer;
}