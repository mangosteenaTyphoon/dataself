package com.shanzhu.dataself.server.dfs;

import com.shanzhu.dataself.framework.openfeign.annotation.EnableTWTFeignClients;
import com.shanzhu.dataself.framework.core.annotation.EnableShanZhuConfig;
import com.shanzhu.dataself.framework.security.annotation.EnableTWTResourceServer;
import com.shanzhu.dataself.framework.swagger.annotation.EnableShanZhuSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: 启动程序
 * @EnableFeignClients 开启服务扫描
 */
@EnableShanZhuSwagger2
@EnableTWTResourceServer
@MapperScan("com.shanzhu.**.mapper")
@EnableShanZhuConfig
@EnableTWTFeignClients
@SpringBootApplication
public class DFSApplication {

	public static void main(String[] args) {
		SpringApplication.run(DFSApplication.class, args);
	}

}
