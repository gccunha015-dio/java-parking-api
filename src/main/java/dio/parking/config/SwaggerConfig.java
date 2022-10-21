package dio.parking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI api() {
    return new OpenAPI()
        .info(new Info()
            .title("Parking API")
            .description("REST API to manage a parking system.")
            .version("v0.0.1"));
  }
}