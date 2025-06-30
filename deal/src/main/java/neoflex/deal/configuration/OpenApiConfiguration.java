package neoflex.deal.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Deal Microservice - Bank Project",
                description = """
                    Microservice responsible for loan application processing. Handles statement submission,
                    offers generation, selection, and final credit calculation. Integrates with Calculator MS for
                    credit offers and terms computation. Uses PostgreSQL for data storage with different entity
                    implementation strategies: separate relations (green), jsonb fields (yellow), and enums as
                    varchar (blue). API endpoints include /deal/statement, /deal/offer/select, and
                    /deal/calculate/{statementId}
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
                        url = "http://localhost:8081/"
                ),
                @Server(
                        description = "Production",
                        url = "http://minecraftslaves.duckdns.org/" //TODO: Change address when deploy MS
                ),
        }
)
public class OpenApiConfiguration {
}
