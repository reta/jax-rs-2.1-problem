package com.example.problem.config;

import org.apache.cxf.jaxrs.openapi.OpenApiFeature;
import org.apache.cxf.jaxrs.swagger.ui.SwaggerUiConfig;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature;
import org.apache.cxf.validation.BeanValidationProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.zalando.problem.ProblemModule;

import com.example.problem.resource.AbstractThrowableProblemExceptionMapper;
import com.example.problem.resource.ValidationExceptionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@SpringBootConfiguration
public class AppConfig {
    @Bean
    OpenApiFeature createOpenApiFeature() {
        final OpenApiFeature openApiFeature = new OpenApiFeature();
        openApiFeature.setSwaggerUiConfig(new SwaggerUiConfig().url("/api/openapi.json"));
        return openApiFeature;
    }
    
    @Bean
    ValidationExceptionMapper validationExceptionMapper() {
        return new ValidationExceptionMapper();
    }
    
    @Bean
    AbstractThrowableProblemExceptionMapper abstractThrowableProblemExceptionMapper() {
        return new AbstractThrowableProblemExceptionMapper();
    }
    
    @Bean
    JAXRSBeanValidationFeature beanValidationFeature() {
        final JAXRSBeanValidationFeature feature = new JAXRSBeanValidationFeature();
        feature.setProvider(new BeanValidationProvider(new DefaultParameterNameProvider()));
        return feature;
    }
    
    @Bean
    ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ProblemModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }
    
    @Bean
    JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider(objectMapper());
    }
}
