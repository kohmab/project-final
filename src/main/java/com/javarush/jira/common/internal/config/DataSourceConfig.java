package com.javarush.jira.common.internal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@Configuration
@RequiredArgsConstructor

public class DataSourceConfig {


    private final Environment env;

    @Bean
    @Profile("test")
    public DataSource testDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .setName(env.getProperty("spring.datasource.test_db_name") + ";NON_KEYWORDS=VALUE")
//                .ignoreFailedDrops(true)
                .build();
    }

    //  TODO @VALUE BLEAT !!!
    @Bean // добавлен, чтобы выполнить условие "создайте два бина". Этот бин можно выкинуть
    @Profile("prod")
    public DataSource prodDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
}
