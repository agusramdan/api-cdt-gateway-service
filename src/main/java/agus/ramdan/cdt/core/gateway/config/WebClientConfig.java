package agus.ramdan.cdt.core.gateway.config;

import agus.ramdan.cdt.core.gateway.flux.WebClientFilterUtils;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final Tracer tracer;

    @Bean
    public WebClient.Builder webClientBuilder(MeterRegistry meterRegistry) {
        return WebClient.builder()
                .defaultHeader("User-Agent", "MyApp-WebClient")
                .filter(loggingFilter())
                .filter(tracingFilter());
    }

    private ExchangeFilterFunction loggingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Sending request to: " + clientRequest.url());
            return WebClientFilterUtils.logRequest(clientRequest);
        });
    }

    private ExchangeFilterFunction tracingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "UNKNOWN";
            return WebClientFilterUtils.addTracingHeader(clientRequest, traceId);
        });
    }

}
