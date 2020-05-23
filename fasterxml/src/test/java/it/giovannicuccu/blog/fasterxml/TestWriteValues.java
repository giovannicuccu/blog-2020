package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.giovannicuccu.blog.fasterxml.model.Persona;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.infra.Blackhole;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TestWriteValues {


    private ObjectMapper objectMapper;
    private ObjectReader objectReaderPersone;
    private ObjectWriter objectWriterPersone;
    private ObjectReader objectReaderPersoneList;
    private ObjectWriter objectWriterPersoneList;


    private CollectionType typeReference =
            TypeFactory.defaultInstance().constructCollectionType(List.class, Persona.class);

    public TestWriteValues() {
        objectMapper=new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectReaderPersoneList=objectMapper.readerFor(Persona.class);
        objectWriterPersoneList=objectMapper.writerFor(Persona.class);
    }

    @Test
    public void fasterXMLStandardValues() throws Exception {
        List<Persona> persone=new ArrayList<>();
        EasyRandom easyRandom = new EasyRandom();
        for (int i=0;i<=10;i++) {
            persone.add(easyRandom.nextObject(Persona.class));
        }
        testMapperPersoneReaderWriterValues(persone, objectReaderPersoneList, objectWriterPersoneList);
    }

    protected void testMapperPersoneReaderWriterValues(List<Persona> persone, ObjectReader jsonReader, ObjectWriter jsonWriter) throws Exception {
        StringWriter stringWriter=new StringWriter();
        SequenceWriter sequenceWriter =jsonWriter.writeValues(stringWriter);
        sequenceWriter.writeAll(persone);
        MappingIterator<Persona> mappingIterator= jsonReader.readValues(new StringReader(stringWriter.toString()));
        List<Persona> personeDeser=mappingIterator.readAll();
    }
}
