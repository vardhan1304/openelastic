package com.tracing.opentelastic;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {


    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private  Tracer tracer;



    @GetMapping("/demo")
    public String demoEndpoint() {
        // Start a new trace and span
        Span span = tracer.spanBuilder("demoEndpoint").startSpan();
        try (Scope scope = span.makeCurrent()) {
            span.setAttribute("demo","");
            span.setAttribute("demo","workng now1");
            span.setAttribute("demo","workng now2");
            logger.info("Demo endpoint accessed");
            return "Hello, OpenTelemetry!";
        } finally {
            span.end();  // Always end the span
        }
    }

    @GetMapping("/demo2")
    public String demoEndpoint2() {
        // Start a new trace and span
        Span span = tracer.spanBuilder("demo 2 calling").startSpan();
        try (Scope scope = span.makeCurrent()) {
            logger.info("Demo endpoint accessed");
            return "Hello, OpenTelemetry!";
        } finally {
            span.end();  // Always end the span
        }
    }
}
