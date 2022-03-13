package com.epam.esm.configuration;

import com.epam.esm.converter.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.InitBinder;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.WebDataBinder;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db/config.properties")
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {

    private final String PROPERTY_MESSAGE = "property/messages";
    private final String UTF_8 = "UTF-8";

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver}")
    private String driverClassName;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(PROPERTY_MESSAGE);
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding(UTF_8);
        return source;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        return ds;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        Set<Converter<?, ?>> converters = new HashSet<>();
        converters.add(new CertificateToCertificateDtoConverter());
        converters.add(new TagDtoToTagConverter());
        converters.add(new TagToTagDtoConverter());
        converters.add(new UpdatingCertificateDtoToCertificateConverter());
        converters.add(new UserToUserDtoConverter());
        converters.add(new UpdatingUserDtoToUserConverter());
        converters.add(new OrderToOrderDtoConverter());
        conversionServiceFactoryBean.setConverters(converters);
        conversionServiceFactoryBean.afterPropertiesSet();
        return conversionServiceFactoryBean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CertificateToCertificateDtoConverter());
        registry.addConverter(new TagDtoToTagConverter());
        registry.addConverter(new TagToTagDtoConverter());
        registry.addConverter(new UpdatingCertificateDtoToCertificateConverter());
        registry.addConverter(new UpdatingUserDtoToUserConverter());
        registry.addConverter(new UserToUserDtoConverter());
        registry.addConverter(new OrderToOrderDtoConverter());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
