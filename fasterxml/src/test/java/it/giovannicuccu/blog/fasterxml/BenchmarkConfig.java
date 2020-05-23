package it.giovannicuccu.blog.fasterxml;

import it.giovannicuccu.blog.fasterxml.model.Persona;
import it.giovannicuccu.blog.fasterxml.model.PersonaForJson;
import org.jeasy.random.EasyRandom;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;

@State(Scope.Benchmark)
public class BenchmarkConfig {
    public List<Persona> persone=new ArrayList<>();
    public PersoneForBenchmark personeForBenchmark=new PersoneForBenchmark();
    public PersoneHolderForBenchmark personeHolderForBenchmark=new PersoneHolderForBenchmark();

    public List<PersonaForJson> personeForJson=new ArrayList<>();
    public PersoneForJsonForBenchmark personeForJsonForBenchmark=new PersoneForJsonForBenchmark();


    //@Param({ "10" })
    public int numPersone=10;


    @Setup(Level.Invocation)
    public void setUp() {
        EasyRandom easyRandom = new EasyRandom();
        for (int i=0;i<=numPersone;i++) {
            persone.add(easyRandom.nextObject(Persona.class));
        }
        personeForBenchmark.setPersona0(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona1(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona2(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona3(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona4(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona5(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona6(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona7(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona8(easyRandom.nextObject(Persona.class));
        personeForBenchmark.setPersona9(easyRandom.nextObject(Persona.class));

        personeHolderForBenchmark.setPersone(persone);

        for (int i=0;i<=numPersone;i++) {
            personeForJson.add(easyRandom.nextObject(PersonaForJson.class));
        }
        personeForJsonForBenchmark.setPersona0(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona1(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona2(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona3(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona4(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona5(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona6(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona7(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona8(easyRandom.nextObject(PersonaForJson.class));
        personeForJsonForBenchmark.setPersona9(easyRandom.nextObject(PersonaForJson.class));
    }
}
