package com.re.it211project.controller;

import com.re.it211project.dto.response.CvResponse;
import com.re.it211project.service.CvService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CvController.class)
class CvControllerTest extends WebMvcTestSupport {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CvService cvService;

    @Test
    void getMyCv_success() throws Exception {
        CvResponse response = CvResponse.builder()
                .cvUrl("https://res.cloudinary.com/demo/cv.pdf")
                .build();

        when(cvService.getMyCv()).thenReturn(response);

        mockMvc.perform(get("/api/v1/cv/my-cv"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.cvUrl").value("https://res.cloudinary.com/demo/cv.pdf"));
    }
}