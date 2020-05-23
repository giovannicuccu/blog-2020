package it.giovannicuccu.blog.fasterxml.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonFormat(shape= JsonFormat.Shape.ARRAY)
@JsonPropertyOrder(alphabetic=true)
public class IndirizzoForJson {
    private String indirizzo;
    private String comune;
    private String provincia;
    private String cap;

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }
}
