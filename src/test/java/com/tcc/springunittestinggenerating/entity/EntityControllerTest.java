package com.tcc.springunittestinggenerating.entity;

import com.tcc.springunittestinggenerating.utils.MockMvcRequestBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EntityController.class)
public class EntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntityService service;

    private static String baseUrl;

    @BeforeAll
    public static void setup(){

        baseUrl = EntityController.class.getDeclaredAnnotation(RequestMapping.class).value()[0];

    }

    @Test
    public void listarTodosTest() {

        final MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.get(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, servletRequestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().expect200();

    }

    @Test
    public void buscarPorIdTest() {

        final MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.get(baseUrl+"/id/1");

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, servletRequestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().expect200();

    }

    @Test
    public void criarTest() {

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(new EntityModel()).contentTypeApplicationJson().expect201();

    }

    @Test
    public void atualizarTest() {

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(baseUrl+"/id/1");

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(new EntityModel()).contentTypeApplicationJson().expect204();

    }

    @Test
    public void excluirTest() {

        final MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders.delete(baseUrl+"/id/1");

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, servletRequestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().expect204();

    }

}
