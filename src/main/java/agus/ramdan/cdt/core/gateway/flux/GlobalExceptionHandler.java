package agus.ramdan.cdt.core.gateway.flux;

import agus.ramdan.base.exception.*;
import io.micrometer.tracing.Tracer;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.MDC;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Tracer tracer;

    private String getTraceId() {
        String traceId = MDC.get("traceId");
        if (traceId == null && tracer.currentSpan() != null && tracer.currentSpan().context() != null) {
            traceId = tracer.currentSpan().context().traceId();
        }
        return traceId;
    }

    private String getSpanId() {
        String spanId = MDC.get("spanId");
        if (spanId == null && tracer.currentSpan() != null) {
            spanId = tracer.currentSpan().context().spanId();
        }
        return spanId;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public Mono<Void> handleResourceNotFound(ResourceNotFoundException ex, ServerWebExchange exchange) {
        return handleException(exchange, HttpStatus.NOT_FOUND, "Resource Not Found", ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<Void> handleValidationException(ConstraintViolationException ex, ServerWebExchange exchange) {
        val errors = ex.getConstraintViolations().stream()
                .map(violation -> new ErrorValidation(violation.getMessage(), String.valueOf(violation.getPropertyPath()), violation.getInvalidValue()))
                .collect(Collectors.toList());
        return handleExceptionWithErrors(exchange, HttpStatus.BAD_REQUEST, "Validation Error", ex.getMessage(), errors.toArray(new ErrorValidation[0]));
    }

    @ExceptionHandler(BadRequestException.class)
    public Mono<Void> handleBadRequest(BadRequestException ex, ServerWebExchange exchange) {
        return handleExceptionWithErrors(exchange, HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), ex.getErrors());
    }

    @ExceptionHandler(NoContentException.class)
    public Mono<Void> handleNoContent(NoContentException ex, ServerWebExchange exchange) {
        log.debug("trace_id={}, span_id={}, message={}", getTraceId(), getSpanId(), ex.getMessage(), ex);
        exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
        return exchange.getResponse().setComplete();
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public Mono<Void> handleInternalServerError(InternalServerErrorException ex, ServerWebExchange exchange) {
        return handleException(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error. Please Contact Helpdesk", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Mono<Void> handleGenericException(Exception ex, ServerWebExchange exchange) {
        return handleException(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error. Please Contact Helpdesk", ex.getMessage());
    }

    private Mono<Void> handleException(ServerWebExchange exchange, HttpStatus status, String message, String details) {
        log.error("trace_id={}, span_id={}, message={}", getTraceId(), getSpanId(), details);
        Errors errorResponse = new Errors(new Date(), message, getTraceId(), getSpanId(), details, null);
        return writeJsonResponse(exchange, status, errorResponse);
    }

    private Mono<Void> handleExceptionWithErrors(ServerWebExchange exchange, HttpStatus status, String message, String details, ErrorValidation ... errors) {
        log.error("trace_id={}, span_id={}, message={}", getTraceId(), getSpanId(), details);
        Errors errorResponse = new Errors(new Date(), message, getTraceId(), getSpanId(), details, errors);
        return writeJsonResponse(exchange, status, errorResponse);
    }

    private Mono<Void> writeJsonResponse(ServerWebExchange exchange, HttpStatus status, Errors errorResponse) {
        try {
            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String json = JsonUtils.toJson(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            log.error("Error writing JSON response: {}", e.getMessage());
            return exchange.getResponse().setComplete();
        }
    }
}
