package jv.supermarket.services.Exceptions;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;
    private List<String> mensages;

    public Mensagem() {
    }

    public Mensagem(Instant timestamp, Integer status, String error, String path, List<String> mensages) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
        this.mensages = mensages;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getMensages() {
        return mensages;
    }

    public void setMensages(List<String> mensages) {
        this.mensages = mensages;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}