package it.giovannicuccu.blog.fasterxml;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import it.giovannicuccu.blog.fasterxml.model.PersonaForJson;

@JsonFormat(shape= JsonFormat.Shape.ARRAY)
@JsonPropertyOrder(alphabetic=true)
public class PersoneForJsonForBenchmark {
    private PersonaForJson persona0;
    private PersonaForJson persona1;
    private PersonaForJson persona2;
    private PersonaForJson persona3;
    private PersonaForJson persona4;
    private PersonaForJson persona5;
    private PersonaForJson persona6;
    private PersonaForJson persona7;
    private PersonaForJson persona8;
    private PersonaForJson persona9;

    public PersonaForJson getPersona0() {
        return persona0;
    }

    public void setPersona0(PersonaForJson persona0) {
        this.persona0 = persona0;
    }

    public PersonaForJson getPersona1() {
        return persona1;
    }

    public void setPersona1(PersonaForJson persona1) {
        this.persona1 = persona1;
    }

    public PersonaForJson getPersona2() {
        return persona2;
    }

    public void setPersona2(PersonaForJson persona2) {
        this.persona2 = persona2;
    }

    public PersonaForJson getPersona3() {
        return persona3;
    }

    public void setPersona3(PersonaForJson persona3) {
        this.persona3 = persona3;
    }

    public PersonaForJson getPersona4() {
        return persona4;
    }

    public void setPersona4(PersonaForJson persona4) {
        this.persona4 = persona4;
    }

    public PersonaForJson getPersona5() {
        return persona5;
    }

    public void setPersona5(PersonaForJson persona5) {
        this.persona5 = persona5;
    }

    public PersonaForJson getPersona6() {
        return persona6;
    }

    public void setPersona6(PersonaForJson persona6) {
        this.persona6 = persona6;
    }

    public PersonaForJson getPersona7() {
        return persona7;
    }

    public void setPersona7(PersonaForJson persona7) {
        this.persona7 = persona7;
    }

    public PersonaForJson getPersona8() {
        return persona8;
    }

    public void setPersona8(PersonaForJson persona8) {
        this.persona8 = persona8;
    }

    public PersonaForJson getPersona9() {
        return persona9;
    }

    public void setPersona9(PersonaForJson persona9) {
        this.persona9 = persona9;
    }
}
