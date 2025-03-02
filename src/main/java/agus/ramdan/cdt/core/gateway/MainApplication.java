package agus.ramdan.cdt.core.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableReactiveMongoRepositories(basePackages = "agus.ramdan.cdt.core.gateway.repository")
// @ComponentScan(basePackages={"agus.ramdan.base.exception","agus.ramdan.cdt.core.gateway"})
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

}
