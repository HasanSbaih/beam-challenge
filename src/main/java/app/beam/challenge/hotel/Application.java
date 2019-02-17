package app.beam.challenge.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Beam Challenge APi ")
                .description("Describes the specification for Beam Challenge Apis")
                .version("1.0.0")
                .contact(new Contact("Hasan Mohammad ","www.linkedin.com/in/hasan-mohammed-9ba570ba", "h.sbaih93@gmail.com"))
                .build();
    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("app.beam.challenge.hotel.port.api"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }
}