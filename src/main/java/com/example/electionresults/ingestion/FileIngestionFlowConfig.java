package com.example.electionresults.ingestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.EndpointId;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transaction.*;
import org.springframework.messaging.MessageChannel;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.io.File;

@Configuration
@EnableIntegration
public class FileIngestionFlowConfig {

    @Value( "${poll.inbound.path}" )
    private String inboundRead;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionSynchronizationFactory transactionSynchronizationFactory;

    @Bean
    public IntegrationFlow controlBus() {
        return IntegrationFlows.from(controlChannel())
                .controlBus()
                .get();
    }

    @Bean
    public MessageChannel controlChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageChannel fileInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel xmlToObjectProcessorChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel electionResultContentValidationProcessorInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel electionResultProcessorInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel electionResultUpdateNotifierProcessorInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel emailChannel() {
        return new DirectChannel();
    }

    @Bean
    @EndpointId("fileReadingSource")
    @InboundChannelAdapter(value = "fileInputChannel", autoStartup = "${poll.autostartup}", poller = @Poller(value = "pollerMetadata"))
    public FileReadingMessageSource fileReadingMessageSource() {

        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.xml"));
        filters.addFilter(new LastModifiedFileFilter());

        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setAutoCreateDirectory(true);
        source.setDirectory(new File(this.inboundRead));
        source.setFilter(filters);

        return source;
    }

    @Bean
    @Transformer(inputChannel = "fileInputChannel", outputChannel = "xmlToObjectProcessorChannel")
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }

    @Bean
    public PollerMetadata pollerMetadata() {
        return Pollers.fixedDelay(1000)
                .errorChannel("errorChannel")
                .errorHandler(null)
                .advice(transactionInterceptor())
                .transactionSynchronizationFactory(transactionSynchronizationFactory)
                .get();
    }

    private TransactionInterceptor transactionInterceptor() {
        return new TransactionInterceptorBuilder()
                .transactionManager(transactionManager)
                .build();
    }

    @Bean
    public ExpressionEvaluatingTransactionSynchronizationProcessor expressionEvaluatingTransactionSynchronizationProcessor() {

        ExpressionEvaluatingTransactionSynchronizationProcessor processor = new ExpressionEvaluatingTransactionSynchronizationProcessor();

        SpelExpressionParser spelParser = new SpelExpressionParser();

        processor.setAfterCommitExpression(
                spelParser.parseExpression(
                        "payload.renameTo(new java.io.File(payload.absolutePath + '.PASSED'))"));

        processor.setAfterRollbackExpression(
                spelParser.parseExpression(
                        "payload.renameTo(new java.io.File(payload.absolutePath + '.FAILED'))"));

        return processor;
    }

    @Bean
    public TransactionSynchronizationFactory transactionSynchronizationFactory() {
        return new DefaultTransactionSynchronizationFactory(expressionEvaluatingTransactionSynchronizationProcessor());
    }
}
