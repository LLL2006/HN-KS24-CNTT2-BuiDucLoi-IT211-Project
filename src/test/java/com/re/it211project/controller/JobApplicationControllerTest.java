package com.re.it211project.controller;

import tools.jackson.databind.ObjectMapper;
import com.re.it211project.dto.request.JobApplicationRequest;
import com.re.it211project.dto.response.JobApplicationResponse;
import com.re.it211project.enums.ApplicationStatus;
import com.re.it211project.service.JobApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobApplicationController.class)
class JobApplicationControllerTest extends WebMvcTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JobApplicationService jobApplicationService;

    @Test
    void applyJob_success() throws Exception {
        JobApplicationRequest request = JobApplicationRequest.builder()
                .jobId(1L)
                .coverLetter("Tôi muốn ứng tuyển")
                .build();

        JobApplicationResponse response = JobApplicationResponse.builder()
                .id(1L)
                .status(ApplicationStatus.PENDING)
                .build();

        when(jobApplicationService.applyJob(any(JobApplicationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/applications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.status").value("PENDING"));
    }
}