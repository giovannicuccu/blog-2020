package it.giovannicuccu.blog.fasterxml;

import it.giovannicuccu.blog.fasterxml.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class PersoneHolderForBenchmark {
    private List<Persona> persone=new ArrayList<>();

    public List<Persona> getPersone() {
        return persone;
    }

    public void setPersone(List<Persona> persone) {
        this.persone = persone;
    }
}
