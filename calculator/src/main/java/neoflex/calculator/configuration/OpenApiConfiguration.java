package neoflex.calculator.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Calculator Microservice - Bank Project",
                description = "This service calculates loan offers and credits using pre-scoring and scoring data from Deal Microservice",
                contact = @Contact(
                        name = "Ukhin Maxim",
                        email = "ukhin.maxim@gmail.com"
                ),
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080/"
                ),
                @Server(
                        description = "Production",
                        url = "http://minecraftslaves.duckdns.org/" //TODO: Change address when deploy MS
                ),
        }
)
public class OpenApiConfiguration {
}
