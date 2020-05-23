package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.giovannicuccu.blog.fasterxml.model.Persona;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FasterXMLPerformance extends  PerformanceBaseClass{

    private ObjectMapper objectMapper;
    private ObjectReader objectReaderPersone;
    private ObjectWriter objectWriterPersone;
    private ObjectReader objectReaderPersoneList;
    private ObjectWriter objectWriterPersoneList;


    private CollectionType typeReference =
            TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class);

    public FasterXMLPerformance() {
        objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectReaderPersone=objectMapper.readerFor(PersoneForBenchmark.class);
        objectWriterPersone=objectMapper.writerFor(PersoneForBenchmark.class);
        objectReaderPersoneList=objectMapper.readerFor(Persona.class);
        objectWriterPersoneList=objectMapper.writerFor(Persona.class);
    }

    public static void main(String[] args) throws IOException {
        org.openjdk.jmh.Main.main(new String[] {"FasterXMLPerformance"});
    }

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandard(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapper(config.persone, bh, objectMapper);
    }*/

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardValues(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersoneReaderWriterValues(config.persone, bh, objectReaderPersoneList, objectWriterPersoneList);
    }*/

/*    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardHolder(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperHolder(config.personeHolderForBenchmark, bh, objectMapper);
    }*/

    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersone(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersone(config.personeForBenchmark, bh, objectMapper);
    }

//    @Fork(value = 1, warmups = 1)
//    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
//    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
//    @BenchmarkMode(Mode.Throughput)
//    @Benchmark
//    public void fasterXMLStandardTypeReference(BenchmarkConfig config, Blackhole bh) throws Exception {
//        testMapperWithTypeReference(config.persone, bh, objectMapper);
//    }

    @Threads(6)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersoneMultiThreadReaderWriter(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersoneReaderWriter(config.personeForBenchmark, bh, objectReaderPersone, objectWriterPersone);
    }

    @Threads(6)
    @Fork(value = 1, warmups = 1)
    @Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    public void fasterXMLStandardPersoneMultiThread(BenchmarkConfig config, Blackhole bh) throws Exception {
        testMapperPersone(config.personeForBenchmark, bh, objectMapper);
    }


}
