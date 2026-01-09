package com.shanzhu.dataself.framework.security.annotation;

import com.shanzhu.dataself.framework.security.config.TWTResourceServerAutoConfiguration;
import com.shanzhu.dataself.framework.security.config.TWTResourceServerConfiguration;
import com.shanzhu.dataself.framework.security.feign.FeignConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.lang.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 资源服务注解
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableMethodSecurity // 开启全局注解安全
@Import({ TWTResourceServerAutoConfiguration.class, TWTResourceServerConfiguration.class, FeignConfig.class })
public @interface EnableTWTResourceServer {

}
