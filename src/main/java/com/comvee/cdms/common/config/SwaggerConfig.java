package com.comvee.cdms.common.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:9094/swagger-ui.html
// http://localhost:9094/doc.html
@Configuration
@EnableSwagger2 //添加swagger启用注解
public class SwaggerConfig {

    @Value("${swagger2.enable:true}")
    private boolean swagger2Enable;

//    @Bean
//    public Docket adminApis() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("后台管理")
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.regex("/back/admin.*"))
//                .build()
//                .apiInfo(apiInfo())
//                .enable(swagger2Enable);
//    }

    @Bean
    public Docket controller2Apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("慢性病智能管理系统api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.comvee.cdms"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(swagger2Enable);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("厦门174医院系统")
                .description("API接口文档")
                .version("1.0.0")
                .build();
    }
}
