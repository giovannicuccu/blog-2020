package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import it.giovannicuccu.blog.fasterxml.model.Persona;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FasterXMLPerformanceSmileAfterBurnerArraySer extends  PerformanceBaseClass{

    private ObjectMapper objectMapper;
    private ObjectReader objectReaderPersone;
    private ObjectWriter objectWriterPersone;

    private CollectionType typeReference =
            TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class);

    public FasterXMLPerformanceSmileAfterBurnerArraySer() {
        objectMapper=new ObjectMapper(new SmileFactory());
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.registerModule(new JavaTimeModule());
        objectReaderPersone=objectMapper.readerFor(PersoneForJsonForBenchmark.class);
        objectWriterPersone=objectMapper.writerFor(PersoneForJsonForBenchmark.class);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandard(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperBinaryForJson(config.personeForJson, bh, objectMapper);
    }*/

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersone(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersoneBinaryForJson(config.personeForJsonForBenchmark, bh, objectMapper);
    }

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardTypeReference(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperWithTypeReferenceBinaryForJson(config.personeForJson, bh, objectMapper);
    }*/

    @Threads(6)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersoneMultiThreadReaderWriter(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersoneReaderWriterBinaryForJson(config.personeForJsonForBenchmark, bh, objectReaderPersone, objectWriterPersone);
    }

    @Threads(6)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersoneMultiThread(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersoneBinaryForJson(config.personeForJsonForBenchmark, bh, objectMapper);
    }



}
