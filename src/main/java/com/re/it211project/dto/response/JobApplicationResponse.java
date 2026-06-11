package com.re.it211project.dto.response;

import com.re.it211project.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationResponse {

    private Long id;

    private String coverLetter;

    private String feedback;

    private String cvUrl;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    private JobResponse job;

    private UserResponse candidate;
}