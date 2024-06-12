package com.compulynx.compas;

import java.util.Properties;

import com.compulynx.compas.config.AppProperties;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

import com.compulynx.compas.config.ResourceConfig;
import reactor.netty.http.client.HttpClient;

//@SpringBootApplication(exclude = 
//                          SpringDataWebAutoConfiguration.class)
//
@SpringBootApplication(exclude = 
{SpringDataWebAutoConfiguration.class,
          DataSourceAutoConfiguration.class, 
            XADataSourceAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class})
@EnableAutoConfiguration(exclude = FlywayAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@EnableTransactionManagement
public class CompasApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CompasApplication.class).properties(getProperties());
    }

    public static void main(String[] args) {
        System.out.println("catalina base ###" + ResourceConfig.CATALINA_BASE);
        SpringApplication.run(CompasApplication.class, args);
    }

    static Properties getProperties() {
        Properties props = new Properties();
        props.put("spring.config.location", ResourceConfig.CATALINA_BASE + "/conf/iprint/");
        return props;
    }

    
//    @Bean
//    WebClient.Builder getWebClientBuilder() {
//        return WebClient.builder();
//    }
    @Bean
    WebClient webClient() {
    HttpClient httpClient = HttpClient.create();
    return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

}

    @Bean
    JRXlsxExporter getCommonXlsxExporter() {
        JRXlsxExporter exporter = new JRXlsxExporter();
        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.isIgnorePageMargins();
        reportConfig.isOnePagePerSheet();
        reportConfig.isDetectCellType();
        reportConfig.isWhitePageBackground();
        reportConfig.isRemoveEmptySpaceBetweenRows();
        reportConfig.isRemoveEmptySpaceBetweenColumns();
        reportConfig.isCollapseRowSpan();
        exporter.setConfiguration(reportConfig);
        return exporter;
    }

    @Bean
    JRCsvExporter getCommonCsvExporter() {
        JRCsvExporter exporter = new JRCsvExporter();
        return exporter;
    }

    @Bean
    public SpringApplicationContext springAppplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    public AppProperties getAppProperties() {
        return new AppProperties();
    }

}
