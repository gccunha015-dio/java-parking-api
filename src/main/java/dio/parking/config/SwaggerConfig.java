package dio.parking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI api(
      @Value("${info.app.version}") String appVersion,
      @Value("${server.servlet.context-path}") String contextPath) {
    return new OpenAPI()
        .addServersItem(new Server().url(contextPath))
        .info(new Info()
            .title("Parking API")
            .description("REST API to manage a parking system.")
            .version(appVersion));
  }
}