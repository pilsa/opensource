package com.pilsa.invest.framework.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * The type H2DataBase server config.
 *
 * @author pilsa_home1
 */
@Slf4j
@Configuration
@Profile("local")
@MapperScan(basePackages = {"com.pilsa.invest.biz.**.mapper"}, sqlSessionFactoryRef = "investSqlSessionFactory")
public class H2ServerConfig {


    /**
     * Data source data source.
     *
     * @return the data source
     * @throws SQLException the sql exception
     */
    @Bean(name = "investDataSource")
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource() throws SQLException {

        Server server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9095").start();
        if (server.isRunning(true)) {
            log.info("h2-server run success");
        }
        log.info("h2-server url = {}", server.getURL());
        return new HikariDataSource();
    }

    /**
     * Invest sql session factory bean sql session factory.
     *
     * @param investDataSource   the invest data source
     * @param applicationContext the application context
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean(name = "investSqlSessionFactory")
    public SqlSessionFactory investSqlSessionFactoryBean(@Qualifier("investDataSource") DataSource investDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(investDataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage(
                "com.pilsa.invest.biz.**.dto"
        );
        sqlSessionFactoryBean.setMapperLocations(ArrayUtils.addAll(
                applicationContext.getResources("classpath:mapper/biz/*.xml")
        ));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * Invest sql session template bean sql session template.
     *
     * @param investSqlSessionFactory the invest sql session factory
     * @return the sql session template
     */
    @Bean(name = "investSqlSessionTemplate")
    public SqlSessionTemplate investSqlSessionTemplateBean(@Qualifier("investSqlSessionFactory") SqlSessionFactory investSqlSessionFactory) {
        return new SqlSessionTemplate(investSqlSessionFactory);
    }

}
