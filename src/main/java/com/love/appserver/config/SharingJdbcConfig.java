package com.love.appserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.*;

/**
 * @ClassName SharingJdbcDataSourceConfig
 * @Description 数据源配置
 * @Author yangjingwei
 * @Date 2021/10/28 10:57
 * @Version 1.0
 **/
@Data
@Configuration
public class SharingJdbcConfig {

    @Value("${sharding.jdbc.datasource.names}")
    private String names;

    @Value("${sharding.jdbc.config.sharding.props.sql.show}")
    private String sqlShow;

    @Value("${sharding.jdbc.config.masterslave.load-balance-algorithm-type}")
    private String loadBalanceAlgorithmType;

    @Value("${sharding.jdbc.config.masterslave.name}")
    private String name;

    @Value("${sharding.jdbc.config.masterslave.master-data-source-name}")
    private String masterDataSourceName;

    @Value("${sharding.jdbc.config.masterslave.slave-data-source-names}")
    private String slaveDataSourceNames;

    @Value("${sharding.jdbc.datasource.ds1.type}")
    private String type1;
    @Value("${sharding.jdbc.datasource.ds1.driver-class-name}")
    private String driverClassName1;
    @Value("${sharding.jdbc.datasource.ds1.url}")
    private String url1;
    @Value("${sharding.jdbc.datasource.ds1.username}")
    private String username1;
    @Value("${sharding.jdbc.datasource.ds1.password}")
    private String password1;
    @Bean
    public DruidDataSource dataSource1() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url1);
        datasource.setUsername(username1);
        datasource.setPassword(password1);
        datasource.setDriverClassName(driverClassName1);
        return datasource;
    }

    @Value("${sharding.jdbc.datasource.ds2.type}")
    private String type2;
    @Value("${sharding.jdbc.datasource.ds2.driver-class-name}")
    private String driverClassName2;
    @Value("${sharding.jdbc.datasource.ds2.url}")
    private String url2;
    @Value("${sharding.jdbc.datasource.ds2.username}")
    private String username2;
    @Value("${sharding.jdbc.datasource.ds2.password}")
    private String password2;
    @Bean
    public DruidDataSource dataSource2() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url2);
        datasource.setUsername(username2);
        datasource.setPassword(password2);
        datasource.setDriverClassName(driverClassName2);
        return datasource;
    }
    @Value("${sharding.jdbc.datasource.ds3.type}")
    private String type3;
    @Value("${sharding.jdbc.datasource.ds3.driver-class-name}")
    private String driverClassName3;
    @Value("${sharding.jdbc.datasource.ds3.url}")
    private String url3;
    @Value("${sharding.jdbc.datasource.ds3.username}")
    private String username3;
    @Value("${sharding.jdbc.datasource.ds3.password}")
    private String password3;
    @Bean
    public DruidDataSource dataSource3() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url3);
        datasource.setUsername(username3);
        datasource.setPassword(password3);
        datasource.setDriverClassName(driverClassName3);
        return datasource;
    }

    @Bean(name = "data1Template")
    public JdbcTemplate data1Template(@Autowired DruidDataSource dataSource1) {
        return new JdbcTemplate(dataSource1);
    }

    @Bean(name = "data2Template")
    public JdbcTemplate data2Template(@Autowired DruidDataSource dataSource2) {
        return new JdbcTemplate(dataSource2);
    }

    @Bean(name = "data3Template")
    public JdbcTemplate data3Template(@Autowired DruidDataSource dataSource3) {
        return new JdbcTemplate(dataSource3);
    }

    @Bean
    public DataSource dataSource(@Autowired DruidDataSource dataSource1, @Autowired DruidDataSource dataSource2,
                                 @Autowired DruidDataSource dataSource3) throws Exception {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.setDefaultDataSourceName("ds1");
        Map<String, DataSource> dataMap = new LinkedHashMap<>();
        dataMap.put("ds1", dataSource1);
        dataMap.put("ds2", dataSource2);
        dataMap.put("ds3", dataSource3);
     /*   // 设置分表规则
        shardingRuleConfiguration.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        // 绑定
        shardingRuleConfiguration.getBindingTableGroups().add("t_order");
        // 设置数据源分片规则
        shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        // 设置数据表分片规则
        shardingRuleConfiguration.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("order_id",new PreciseModuloShardingTableAlgorithm()));*/
        // 配置读写分离规则
        shardingRuleConfiguration.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigs());
        Properties prop = new Properties();
        return ShardingDataSourceFactory.createDataSource(dataMap, shardingRuleConfiguration, new HashMap<>(), prop);
    }
    // 读写分离策略
    List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigs(){
        // 主从配置
        List<String> slaveDataSourceNames=new ArrayList<>();
        slaveDataSourceNames.add("ds2");
        slaveDataSourceNames.add("ds3");
        MasterSlaveRuleConfiguration masterSlave01 = new MasterSlaveRuleConfiguration("ds1","ds1",slaveDataSourceNames,null);
        return Lists.newArrayList(masterSlave01);
    }
}
