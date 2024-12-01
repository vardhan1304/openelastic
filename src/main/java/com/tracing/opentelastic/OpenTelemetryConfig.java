package com.tracing.opentelastic;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

//    @Bean
//    public OpenTelemetry openTelemetry() {
//        OtlpGrpcSpanExporter exporter = OtlpGrpcSpanExporter.builder()
//                .setEndpoint("http://otel-collector:4317")
//                .build();
//
//        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
//                .addSpanProcessor(SimpleSpanProcessor.create(exporter))
//                .build();
//
//        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
//                .setTracerProvider(tracerProvider)
//                .build();
//
//        GlobalOpenTelemetry.set(openTelemetry);
//        return openTelemetry;
//    }
//
//    @Bean
//    public Tracer tracer(OpenTelemetry openTelemetry) {
//        // Return the default Tracer instance
//        return openTelemetry.getTracer("com.tracing.opentelastic", "1.0");
//    }
@Bean
public OpenTelemetry initOpenTelemetry() {

    // OTLP exporter for spans
    OtlpGrpcSpanExporter spanExporter = OtlpGrpcSpanExporter.builder()
            .setEndpoint("http://otel-collector:4317") // Set your OTLP collector endpoint
            .build();

    // OTLP exporter for logs
    OtlpGrpcLogRecordExporter logsExporter = OtlpGrpcLogRecordExporter.builder()
            .setEndpoint("http://otel-collector:4317") // Set your OTLP collector endpoint
            .build();

    // Span processor for batch exporting
    BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(spanExporter).build();

    // Trace provider setup
    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
            .addSpanProcessor(spanProcessor)
            .build();

    // Log record processor setup with OTLP exporter for logs
    BatchLogRecordProcessor logRecordProcessor = BatchLogRecordProcessor.builder(logsExporter)
            .build();  // Use BatchLogRecordProcessor for logs

    // Logger provider setup
    SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
            .addLogRecordProcessor(logRecordProcessor)  // Add log record processor
            .build();

    // OpenTelemetry SDK setup
    OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
            .setTracerProvider(tracerProvider)
            .setLoggerProvider(loggerProvider)  // Set the logger provider here
            .build();

    // Set OpenTelemetry globally
    return openTelemetrySdk;
    }


    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {
        return openTelemetry.getTracer("com.example.demo", "1.0");
    }
}
