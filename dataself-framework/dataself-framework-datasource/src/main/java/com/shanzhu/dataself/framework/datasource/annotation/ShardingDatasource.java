package com.shanzhu.dataself.framework.datasource.annotation;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * @author shanzhu
 * @WebSite shanzhu.cloud
 * @Description: Sharding JDBC数据源
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("shardingSphere")
public @interface ShardingDatasource {

}
