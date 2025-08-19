package neoflex.gateway.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Api Gateway - Bank Project",
                description = """
                    API Gateway for the Bank Project, facilitating communication between various microservices.
                    This gateway routes requests to the appropriate microservices, including Deal, Statement,
                    and Calculator. It handles authentication, authorization, and request aggregation.
                    The gateway is designed to simplify client interactions with the backend services, providing a
                    unified entry point for all API requests. Key endpoints include /deal, /statement, and
                    /calculator, which correspond to their respective microservices.
                    """,
                contact = @Contact(
                        name = "Ukhin Maxim",
                        email = "ukhin.maxim@gmail.com"
                ),
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8888/"
                ),
                @Server(
                        description = "Production",
                        url = "http://minecraftslaves.duckdns.org:8888/" //TODO: Change address when deploy MS
                ),
        }
)
public class OpenApiConfiguration {
}
