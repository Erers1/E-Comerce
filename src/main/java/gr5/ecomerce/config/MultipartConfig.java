package gr5.ecomerce.config;

import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartConfig {

    @Bean
    public TomcatServletWebServerFactory tomcatEmbeddedWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector ->
                connector.setMaxPostSize(50 * 1024 * 1024) // 50MB
        );
        return factory;
    }
}
