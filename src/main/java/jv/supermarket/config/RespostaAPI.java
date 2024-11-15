package jv.supermarket.config;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RespostaAPI implements Serializable{
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private String message;


    
    public RespostaAPI(Instant timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }


    public RespostaAPI() {
    }

    
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    


}
