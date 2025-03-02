package agus.ramdan.cdt.core.gateway.flux;

import org.springframework.web.reactive.function.client.ClientRequest;
import reactor.core.publisher.Mono;

public class WebClientFilterUtils {
    public static Mono<ClientRequest> logRequest(ClientRequest request) {
        System.out.println("Request: " + request.method() + " " + request.url());
        return Mono.just(request);
    }

    public static Mono<ClientRequest> addTracingHeader(ClientRequest request, String traceId) {
        ClientRequest newRequest = ClientRequest.from(request)
                .header("X-Trace-Id", traceId)
                .build();
        return Mono.just(newRequest);
    }
}
