package com.shanzhu.dataself.server.gen;

import com.shanzhu.dataself.framework.openfeign.annotation.EnableTWTFeignClients;
import com.shanzhu.dataself.framework.core.annotation.EnableShanZhuConfig;
import com.shanzhu.dataself.framework.datasource.annotation.EnableDynamicDataSource;
import com.shanzhu.dataself.framework.security.annotation.EnableTWTResourceServer;
import com.shanzhu.dataself.framework.swagger.annotation.EnableShanZhuSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 启动器
 */
@EnableDynamicDataSource
@EnableShanZhuSwagger2
@EnableTWTResourceServer
@MapperScan("com.shanzhu.**.mapper")
@EnableShanZhuConfig
@EnableTWTFeignClients
@SpringBootApplication
public class GenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenApplication.class, args);
	}

}
