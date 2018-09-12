package com.example.demo.config;

import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*
*
 * SwaggerConfig
 * <p>
 * SpringBootĬ���Ѿ���classpath:/META-INF/resources/��classpath:/META-INF/resources/webjars/ӳ��
 * ���Ը÷�������Ҫ��д�������SpringMVC�У�������Ҫ��д���壨��û�г��ԣ�
 * ��д�÷�����Ҫ extends WebMvcConfigurerAdapter
 * <p>
 * ")
 * //                .addResourceLocations("classpath:/META-INF/resources/webjars/");
 * //    }
*/

/**
 * @author cdx
 * Created on 2018/9/4.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                Class<?> declaringClass = input.declaringClass();
                if (declaringClass == BasicErrorController.class) {
                    return false;
                }// �ų�

                if (declaringClass.isAnnotationPresent(RestController.class)) {
                    return true;
                } // ��ע�����

                return input.isAnnotatedWith(ResponseBody.class);

            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //�����
                .title("����ʶ��ӿ�api")
                        //�汾
                .version("1.0")
                .build();
    }
}