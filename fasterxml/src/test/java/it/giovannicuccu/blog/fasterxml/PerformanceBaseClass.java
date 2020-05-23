package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import it.giovannicuccu.blog.fasterxml.model.Persona;
import it.giovannicuccu.blog.fasterxml.model.PersonaForJson;
import org.jeasy.random.EasyRandom;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
public class PerformanceBaseClass {


    private CollectionType typeReference =
            TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class);

    private CollectionType typeReferenceForJson =
            TypeFactory.defaultInstance().constructCollectionType(List.class, PersonaForJson.class);



    protected void testMapperWithTypeReference(List<Persona> persone, Blackhole bh, ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(persone);
        bh.consume(personeAsStr);
        List<Persona> personeDeser=jsonMapper.readValue(personeAsStr,typeReference);
        bh.consume(personeDeser);
    }

    protected void testMapperWithTypeReferenceBinary(List<Persona> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(persone);
        bh.consume(personeAsBytes);
        List<Persona> personeDeser=jsonMapper.readValue(personeAsBytes,typeReference);
        bh.consume(personeDeser);
    }

    protected void testMapperWithTypeReferenceForJson(List<PersonaForJson> persone, Blackhole bh, ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(persone);
        bh.consume(personeAsStr);
        List<PersonaForJson> personeDeser=jsonMapper.readValue(personeAsStr,typeReferenceForJson);
        bh.consume(personeDeser);
    }

    protected void testMapperWithTypeReferenceBinaryForJson(List<PersonaForJson> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(persone);
        bh.consume(personeAsBytes);
        List<PersonaForJson> personeDeser=jsonMapper.readValue(personeAsBytes,typeReferenceForJson);
        bh.consume(personeDeser);
    }


    protected void testMapper(List<Persona> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(persone);
        bh.consume(personeAsStr);
        List<Persona> personeDeser=jsonMapper.readValue(personeAsStr,List.class);
        bh.consume(personeDeser);
    }



    protected void testMapperHolder(PersoneHolderForBenchmark persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(persone);
        bh.consume(personeAsStr);
        PersoneHolderForBenchmark personeDeser=jsonMapper.readValue(personeAsStr,PersoneHolderForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperBinary(List<Persona> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(persone);
        bh.consume(personeAsBytes);
        List<Persona> personeDeser=jsonMapper.readValue(personeAsBytes,List.class);
        bh.consume(personeDeser);
    }

    protected void testMapperForJson(List<PersonaForJson> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(persone);
        bh.consume(personeAsStr);
        List<PersonaForJson> personeDeser=jsonMapper.readValue(personeAsStr,List.class);
        bh.consume(personeDeser);
    }

    protected void testMapperBinaryForJson(List<PersonaForJson> persone, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(persone);
        bh.consume(personeAsBytes);
        List<PersonaForJson> personeDeser=jsonMapper.readValue(personeAsBytes,List.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersone(PersoneForBenchmark personeForBenchmark, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(personeForBenchmark);
        bh.consume(personeAsStr);
        PersoneForBenchmark personeDeser=jsonMapper.readValue(personeAsStr,PersoneForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneReaderWriter(PersoneForBenchmark personeForBenchmark, Blackhole bh, ObjectReader jsonReader, ObjectWriter jsonWriter) throws Exception {
        String personeAsStr=jsonWriter.writeValueAsString(personeForBenchmark);
        bh.consume(personeAsStr);
        PersoneForBenchmark personeDeser=jsonReader.readValue(personeAsStr,PersoneForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneReaderWriterValues(List<Persona> persone, Blackhole bh, ObjectReader jsonReader, ObjectWriter jsonWriter) throws Exception {
        StringWriter stringWriter=new StringWriter();
        SequenceWriter sequenceWriter =jsonWriter.writeValues(stringWriter);
        sequenceWriter.writeAll(persone);
        bh.consume(stringWriter.toString());
        MappingIterator<Persona> mappingIterator= jsonReader.readValues(new StringReader(stringWriter.toString()));
        List<Persona> personeDeser=mappingIterator.readAll();
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneReaderWriterBinary(PersoneForBenchmark personeForBenchmark, Blackhole bh, ObjectReader jsonReader, ObjectWriter jsonWriter) throws Exception {
        byte[] personeAsBytes=jsonWriter.writeValueAsBytes(personeForBenchmark);
        bh.consume(personeAsBytes);
        PersoneForBenchmark personeDeser=jsonReader.readValue(personeAsBytes,PersoneForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneReaderWriterBinaryForJson(PersoneForJsonForBenchmark personeForBenchmark, Blackhole bh, ObjectReader jsonReader, ObjectWriter jsonWriter) throws Exception {
        byte[] personeAsBytes=jsonWriter.writeValueAsBytes(personeForBenchmark);
        bh.consume(personeAsBytes);
        PersoneForJsonForBenchmark personeDeser=jsonReader.readValue(personeAsBytes,PersoneForJsonForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneBinary(PersoneForBenchmark personeForBenchmark, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(personeForBenchmark);
        bh.consume(personeAsBytes);
        PersoneForBenchmark personeDeser=jsonMapper.readValue(personeAsBytes,PersoneForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneForJson(PersoneForJsonForBenchmark personeForBenchmark, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        String personeAsStr=jsonMapper.writeValueAsString(personeForBenchmark);
        bh.consume(personeAsStr);
        PersoneForJsonForBenchmark personeDeser=jsonMapper.readValue(personeAsStr,PersoneForJsonForBenchmark.class);
        bh.consume(personeDeser);
    }

    protected void testMapperPersoneBinaryForJson(PersoneForJsonForBenchmark personeForBenchmark, Blackhole bh,ObjectMapper jsonMapper) throws Exception {
        byte[] personeAsBytes=jsonMapper.writeValueAsBytes(personeForBenchmark);
        bh.consume(personeAsBytes);
        PersoneForJsonForBenchmark personeDeser=jsonMapper.readValue(personeAsBytes,PersoneForJsonForBenchmark.class);
        bh.consume(personeDeser);
    }

}
