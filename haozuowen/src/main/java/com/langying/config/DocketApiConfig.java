package com.langying.config;
import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import groovy.transform.CompileStatic;
import groovy.transform.TypeChecked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
/**
 * Created by chenxu on 2016/3/14.
 */
@Configuration
public class DocketApiConfig {

    @Autowired
    private TypeResolver typeResolver;
    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2) .apiInfo(apiInfo()) .select() //1
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.and(springfox.documentation.builders.PathSelectors.ant("/*/**"),Predicates.not(springfox.documentation.builders.PathSelectors.ant("/error"))))
                .build()
                .pathMapping("/") //2
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(HttpSession.class)
                // .genericModelSubstitutes("")
                .alternateTypeRules(springfox.documentation.schema.AlternateTypeRules.newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class)), typeResolver
                                .resolve(ResponseEntity.class,WildcardType.class)),springfox.documentation.schema.AlternateTypeRules.newMapRule(WildcardType.class, WildcardType.class),
                        getAlternateTypeRule(Collection.class, WildcardType.class, List.class,
                                WildcardType.class),
                        getAlternateTypeRule(Resources.class, WildcardType.class, Resources.class),
                        getAlternateTypeRule(PagedResources.class, WildcardType.class,
                                PagedResources.class))
                .useDefaultResponseMessages(false)
                /*.globalResponseMessage(RequestMethod.GET,//<12>
                newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("500 message")
                        .responseModel(new ModelRef("Error"))//<13>
                        .build()))*/
                ;
        /* .alternateTypeRules( //5
        /* getAernateTypeRule(Collection.class, WildcardType.class, List.class,
                 WildcardType.class),
         new AlternateTypeRule(typeResolver.resolve(DeferredResult.class,
                 typeResolver.resolve(ResponseEntity.class, ResponseWrapper.class)),
                 typeResolver.resolve(ResponseWrapper.class,WildcardType.class)),
         new AlternateTypeRule(typeResolver.resolve(ResponseEntity.class),
                 typeResolver.resolve(ResponseEntity.class,  typeResolver.resolve(ResponseWrapper.class, WildcardType.class))),*/
              /*  new AlternateTypeRule(typeResolver.resolve(Map.class), typeResolver.resolve(Object.class)),
                new AlternateTypeRule(typeResolver.resolve(Map.class, String.class, Object.class),
                        typeResolver.resolve(Object.class)),
                new AlternateTypeRule(typeResolver.resolve(Map.class, Object.class, Object.class),
                        typeResolver.resolve(Object.class)),
                new AlternateTypeRule(typeResolver.resolve(Map.class, String.class, String.class),
                        typeResolver.resolve(Object.class)),
        )
             //   .genericModelSubstitutes(ResponseEntity.class) //4
                .useDefaultResponseMessages(false) //6*/

    }
    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("山东好作文接口文档")
                .description("山东好作文接口文档")
                .contact("xu_chen@17english.com")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
    public AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType,
                                                  Type targetType, Type targetGenericType) {
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType source = typeResolver.resolve(sourceType, sourceGenericType);
        ResolvedType target = typeResolver.resolve(targetType, targetGenericType);
        AlternateTypeRule collectionRule = new AlternateTypeRule(source, target);
        return collectionRule;
    }

    public AlternateTypeRule getAlternateTypeRule(Type sourceType, Type sourceGenericType,
                                                  Type targetType) {
        TypeResolver typeResolver = new TypeResolver();
        ResolvedType source = typeResolver.resolve(sourceType, sourceGenericType);
        ResolvedType target = typeResolver.resolve(targetType);
        AlternateTypeRule collectionRule = new AlternateTypeRule(source, target);
        return collectionRule;
    }
}
