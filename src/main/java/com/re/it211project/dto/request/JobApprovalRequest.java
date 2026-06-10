package com.re.it211project.dto.request;

import com.re.it211project.enums.JobStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApprovalRequest {

    @NotNull(message = "Trạng thái duyệt không được để trống")
    private JobStatus status;
}