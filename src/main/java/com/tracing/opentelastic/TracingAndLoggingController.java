//package com.tracing.opentelastic;
//
//import io.opentelemetry.api.common.AttributeKey;
//import io.opentelemetry.api.logs.Logger;
//import io.opentelemetry.api.logs.Severity;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.Tracer;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class TracingAndLoggingController {
//
//    private final Tracer tracer;
//    private final Logger logger;
//
//    // Constructor injection for Tracer and Logger
//    public TracingAndLoggingController(Tracer tracer, Logger logger) {
//        this.tracer = tracer;
//        this.logger = logger;
//    }
//
//    @GetMapping("/trace-and-log")
//    public String traceAndLog() {
//        // Start a new span for the request
//        Span span = tracer.spanBuilder("trace-and-log-span").startSpan();
//
//        try {
//            // Create an info-level log record with attributes using LogRecordBuilder
//            logger.logRecordBuilder()
//                    .setSeverity(Severity.INFO)
//                    .setBody("Processing the request")
//                    .setAttribute(AttributeKey.stringKey("http.method"), "GET")
//                    .setAttribute(AttributeKey.stringKey("http.route"), "/trace-and-log")
//                    .build();  // Finalize and build the log record
//            logger.emit(logger.logRecordBuilder().build());  // Emit the log record
//
//            // Simulate some processing logic (e.g., database call, external API, etc.)
//            performSomeBusinessLogic();
//
//            // Add more log if needed
//            logger.logRecordBuilder()
//                    .setSeverity(Severity.INFO)
//                    .setBody("Request processed successfully")
//                    .setAttribute(AttributeKey.stringKey("http.method"), "GET")
//                    .setAttribute(AttributeKey.stringKey("http.route"), "/trace-and-log")
//                    .build();  // Finalize and build the success log record
//            logger.emit(logger.logRecordBuilder().build());  // Emit the log record
//
//            return "Tracing and logging completed successfully!";
//        } catch (Exception e) {
//            // Create an error log record with exception details
//            logger.logRecordBuilder()
//                    .setSeverity(Severity.ERROR)
//                    .setBody("An error occurred while processing the request")
//                    .setAttribute(AttributeKey.stringKey("http.method"), "GET")
//                    .setAttribute(AttributeKey.stringKey("http.route"), "/trace-and-log")
//                    .setAttribute(AttributeKey.stringKey("exception"), e.getMessage())
//                    .build();  // Finalize and build the error log record
//            logger.emit(logger.logRecordBuilder().build());  // Emit the error log record
//
//            // Record exception in the span
//            span.recordException(e);
//
//            return "Error occurred while processing the request.";
//        } finally {
//            // End the span after the request is processed
//            span.end();
//        }
//    }
//
//    // Simulating some business logic that could be traced
//    private void performSomeBusinessLogic() throws InterruptedException {
//        // Simulate a task (e.g., database query, API call)
//        Thread.sleep(100);  // Simulate delay
//    }
//}
