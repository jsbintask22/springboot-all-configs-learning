package cn.jsbintask.springbootallconfigs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.ServletContext;
import java.net.URI;

/**
 * @author jsbintask@gmail.com
 * @date 2019/2/15 17:28
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ServletContext servletContext;

    @Test
    public void test1() throws Exception{
        MvcResult end = mockMvc.perform(requestBuilder("/hello"))
                .andExpect(mvcResult -> {
                    if (mvcResult.getResponse().getStatus() != 200) {
                        throw new RuntimeException("failed.");
                    }
                })
                .andExpect(result -> {
                    if (!result.getResponse().getContentType().contains("json")) {
                        throw new RuntimeException("failed");
                    }
                }).andReturn();

        System.out.println(end);
    }

    private RequestBuilder requestBuilder(String uri) {
        return MockMvcRequestBuilders.get(URI.create(uri)).accept(MediaType.APPLICATION_JSON_UTF8)
                .characterEncoding("UTF-8");
    }
}
