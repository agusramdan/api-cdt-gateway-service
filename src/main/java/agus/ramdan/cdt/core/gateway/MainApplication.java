package agus.ramdan.cdt.core.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
//@EnableReactiveMongoRepositories(basePackages = "agus.ramdan.cdt.core.gateway.repository")
// @ComponentScan(basePackages={"agus.ramdan.base.exception","agus.ramdan.cdt.core.gateway"})
public class MainApplication {

	public static void main(String[] args) {
		Hooks.enableAutomaticContextPropagation(); // Spring Boot 3 otomatis menangani tracing di Reactor
		SpringApplication.run(MainApplication.class, args);
	}

}
