package com.shanzhu.dataself.auth;

import com.shanzhu.dataself.framework.openfeign.annotation.EnableTWTFeignClients;
import com.shanzhu.dataself.framework.core.annotation.EnableShanZhuConfig;
import com.shanzhu.dataself.framework.swagger.annotation.EnableShanZhuSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 认证中心启动器
 */
@EnableShanZhuSwagger2
@EnableShanZhuConfig
@EnableTWTFeignClients
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

}
