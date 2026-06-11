package com.re.it211project.controller;

import com.re.it211project.dto.response.JobResponse;
import com.re.it211project.enums.JobStatus;
import com.re.it211project.service.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
class JobControllerTest extends WebMvcTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Test
    void searchJobs_success() throws Exception {
        JobResponse job = JobResponse.builder()
                .id(1L)
                .title("Java Backend Developer")
                .status(JobStatus.APPROVED)
                .build();

        when(jobService.searchJobs(anyString(), anyString(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(job)));

        mockMvc.perform(get("/api/v1/jobs/search")
                        .param("keyword", "java")
                        .param("location", "Ha Noi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].title").value("Java Backend Developer"));
    }
}