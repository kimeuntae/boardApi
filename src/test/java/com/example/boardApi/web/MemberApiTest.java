package com.example.boardApi.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class MemberApiTest {

    @Autowired MemberApi memberApi;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMemberList() throws Exception {
        String expectByUsername = "$.[?(@.email == '%s')]";
        String addressByCity = "$..address[?(@.city == '%s')]";

        mockMvc.perform(
                MockMvcRequestBuilders.get("/member/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(expectByUsername, "email@email122.com").exists());
                //.andExpect((ResultMatcher) content().json());
    }



}
