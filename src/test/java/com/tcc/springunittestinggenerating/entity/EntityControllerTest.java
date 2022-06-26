package com.tcc.springunittestinggenerating.entity;

import com.tcc.springunittestinggenerating.controller.EntityController;
import com.tcc.springunittestinggenerating.model.EntityModel;
import com.tcc.springunittestinggenerating.service.EntityService;
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

import static com.tcc.springunittestinggenerating.utils.StringBounderPopulator.initializeSizeAnnotedStringFields;
import static com.tcc.springunittestinggenerating.utils.enums.BounderTestType.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EntityController.class)
public class EntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntityService service;

    //@MockBean
    //private Field service = ReflectionUtils.findFields(EntityController.class, field -> field.isAnnotationPresent(Autowired.class), TOP_DOWN).stream().findFirst().get();

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
    public void criarTest_success_minimum_allowed_string_size() {

        final EntityModel entityModel = new EntityModel();

        initializeSizeAnnotedStringFields(entityModel, MINIMUM_ALLOWED);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(entityModel).contentTypeApplicationJson().expect201();

    }

    @Test
    public void criarTest_success_maximum_allowed_string_size() {

        final EntityModel entityModel = new EntityModel();

        initializeSizeAnnotedStringFields(entityModel, MAXIMUM_ALLOWED);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(entityModel).contentTypeApplicationJson().expect201();

    }

    @Test
    public void criarTest_fail_string_size_underflow() {

        final EntityModel entityModel = new EntityModel();

        initializeSizeAnnotedStringFields(entityModel, UNDERFLOW);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(entityModel).contentTypeApplicationJson().expect400();

    }

    @Test
    public void criarTest_fail_string_size_overflow() {

        final EntityModel entityModel = new EntityModel();

        initializeSizeAnnotedStringFields(entityModel, OVERFLOW);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl);

        final MockMvcRequestBuilder mockMvcRequestBuilder = new MockMvcRequestBuilder(this.mockMvc, requestBuilder);

        mockMvcRequestBuilder.acceptApplicationJson().content(entityModel).contentTypeApplicationJson().expect400();

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
