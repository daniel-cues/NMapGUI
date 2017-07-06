package com.uniovi.nmapgui;


import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class NMapGuiApplicationTests {
	
	@Autowired
    private MockMvc mockMvc;

    @Test
    public void basicTest() throws Exception {
    	assertNotNull("mockMvc couldn't be initiated", mockMvc);
        this.mockMvc.perform(get("/nmap")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Port Scan")));
    }
	
}
