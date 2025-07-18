package neoflex.statement.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Statement Microservice - Bank Project",
                description = """
                        The Statement microservice processes loan applications through a two-step workflow:
                        first evaluating client data to perform pre-screening and generating multiple ranked loan offers
                        with varying terms, then handling the client's selection of their preferred offer
                        and forwarding it for further processing.
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
                        url = "http://localhost:8082/"
                ),
                @Server(
                        description = "Production",
                        url = "http://minecraftslaves.duckdns.org/" //TODO: Change address when deploy MS
                ),
        }
)
public class OpenApiConfiguration {
}
