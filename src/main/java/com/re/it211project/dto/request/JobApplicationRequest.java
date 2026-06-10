package com.re.it211project.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationRequest {

    @NotNull(message = "Job id không được để trống")
    private Long jobId;

    private String coverLetter;

}