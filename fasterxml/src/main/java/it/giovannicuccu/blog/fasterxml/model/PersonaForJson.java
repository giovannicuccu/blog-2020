package it.giovannicuccu.blog.fasterxml.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonFormat(shape= JsonFormat.Shape.ARRAY)
@JsonPropertyOrder(alphabetic=true)
public class PersonaForJson {
    private String codiceFiscale;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private Indirizzo residenza;
    private Indirizzo domicilio;
    private String cellulare;
    private String email;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Indirizzo getResidenza() {
        return residenza;
    }

    public void setResidenza(Indirizzo residenza) {
        this.residenza = residenza;
    }

    public Indirizzo getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Indirizzo domicilio) {
        this.domicilio = domicilio;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
