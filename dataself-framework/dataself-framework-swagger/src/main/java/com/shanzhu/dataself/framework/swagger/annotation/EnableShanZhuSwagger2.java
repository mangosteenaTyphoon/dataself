package com.shanzhu.dataself.framework.swagger.annotation;

import com.shanzhu.dataself.framework.swagger.properties.SwaggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 开启 swagger
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(SwaggerProperties.class)
public @interface EnableShanZhuSwagger2 {

}
