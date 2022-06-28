package com.axing.common.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/***
 * @author qingfeng.zhao
 * @date 2022/3/26
 * @apiNote
 */
@EnableOpenApi
@Configuration
public class SpringFoxSwaggerConfig {
    /**
     * 配置基本信息
     *
     * @return
     */
    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 设置标题
                .title("阿星项目_接口文档")
                // 描述
                .description("描述: xxxx")
                // 作者信息
                .contact(new Contact("axing", null, null))
                // 版本
                .version("版本号:" + "1.1.0")
                .build();
    }

    /**
     * 配置文档生成最佳实践
     *
     * @param apiInfo
     * @return
     */
    @Bean
    public Docket createRestApi(ApiInfo apiInfo) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo)
                .groupName("默认分组")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }
}
