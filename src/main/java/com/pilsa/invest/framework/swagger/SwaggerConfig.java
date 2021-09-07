package com.pilsa.invest.framework.swagger;

import com.pilsa.invest.common.constant.ApiConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * swagger 설정
 *
 * @author : pilsa_internet
 * @since : 2021-07-12 오후 4:03
 */
@Profile("!prd")
@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "개발1팀 기술세미나";
    private static final String API_VERSION = "0.1";
    private static final String API_DESCRIPTION = "이진영 (11078) - 5주에 1회는 좀 그런거 같아요.";

    @Bean
    public Docket api(){

        RequestParameterBuilder parameterBuilder = new RequestParameterBuilder()
                .in(ParameterType.HEADER)
                .name("Authorization")
                .required(true)
                .query(param -> param.model(model -> model.scalarModel(ScalarType.STRING)));


        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(
                        Collections.singletonList(new RequestParameterBuilder()
                                .name(ApiConstant.X_USER_ID)
                                .description("사용자 식별값")
                                .in(ParameterType.HEADER)
                                .required(false)
                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                                .build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION)
                .build();
    }
}
