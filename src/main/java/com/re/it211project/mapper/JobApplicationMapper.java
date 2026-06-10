package com.re.it211project.mapper;

import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.entity.JobApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobApplicationMapper {

    private final JobMapper jobMapper;
    private final UserMapper userMapper;

    public JobApplicationResponse toResponse(JobApplication application) {
        if (application == null) {
            return null;
        }

        return JobApplicationResponse.builder()
                .id(application.getId())
                .coverLetter(application.getCoverLetter())
                .cvUrl(application.getCandidate().getCvUrl())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .job(jobMapper.toResponse(application.getJob()))
                .candidate(userMapper.toResponse(application.getCandidate()))
                .build();
    }
}