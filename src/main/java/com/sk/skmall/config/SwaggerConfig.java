package com.sk.skmall.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private String env = System.getProperty("spring.profiles.active");

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("커머스 프로젝트 API")
                        .description("쇼핑몰 사이트를 구성하는 데 필요한 api를 확인할 수 있습니다.")
                        .version(env));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        // pathsToMatch로 원하는 경로의 api만 나오도록 설정
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .build();
    }

    /**
     * admin과 관련된 api만 적용
     * @return
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        String title = "SKmall Swagger UI";
        String description = "You can check API";

        Info info = new Info().title(title).description(description).version(env);

        return new OpenAPI().info(info);
    }

    public ApiResponse createApiResponse(String message, Content content){
        return new ApiResponse().description(message).content(content);
    }

    @Bean
    public GlobalOpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> {
            // 공통으로 사용되는 response 설정
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
                ApiResponses apiResponses = operation.getResponses();
                apiResponses.addApiResponse("200", createApiResponse(apiResponses.get("200").getDescription(), apiResponses.get("200").getContent()));
                apiResponses.addApiResponse("400", createApiResponse("Bad Request", null));
                apiResponses.addApiResponse("401", createApiResponse("Access Token Error", null));
                apiResponses.addApiResponse("500", createApiResponse("Server Error", null));
            }));
        };
    }
}
