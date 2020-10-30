package config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SwaggerQualityApiConfig {

    @Bean
    // todo groupname werkt ook niet ... met path
    public Docket qualityApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("QualitApi")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/qualityControl/files/**"))
                .build()
                .apiInfo(getApiInfo());
    }


// @todo: denk niet dat dit echt werkt
    ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Quality Control Api")
                .version("1.0")
                .description("API for file quality control")
                .contact(new Contact("Laura Devriendt", "https://learningjava.com","test@hotmail.com"))
                .license("Apache License Version 2.0")
                .build();
    }

}
