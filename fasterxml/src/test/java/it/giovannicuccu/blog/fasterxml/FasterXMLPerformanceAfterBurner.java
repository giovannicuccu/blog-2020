package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import it.giovannicuccu.blog.fasterxml.model.Persona;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.Throughput)
public class FasterXMLPerformanceAfterBurner  extends PerformanceBaseClass {

    private ObjectMapper objectMapper;


    private CollectionType typeReference =
            TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class);

    public FasterXMLPerformanceAfterBurner() {
        objectMapper =new ObjectMapper();
        objectMapper.registerModule(new AfterburnerModule());
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(args);
    }

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Benchmark
    public void fasterXMLStandard(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapper(config.persone, bh, objectMapper);
    }*/

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Benchmark
    public void fasterXMLStandardPersone(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersone(config.personeForBenchmark, bh, objectMapper);
    }

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Benchmark
    public void fasterXMLStandardTypeReference(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperWithTypeReference(config.persone, bh, objectMapper);
    }*/


}
