package com.re.it211project.mapper;

import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobMapper {

    private final CompanyMapper companyMapper;
    private final UserMapper userMapper;

    public JobResponse toResponse(Job job) {
        if (job == null) {
            return null;
        }

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .salary(job.getSalary())
                .deadline(job.getDeadline())
                .status(job.getStatus())
                .company(companyMapper.toResponse(job.getCompany()))
                .employer(userMapper.toResponse(job.getEmployer()))
                .build();
    }
}