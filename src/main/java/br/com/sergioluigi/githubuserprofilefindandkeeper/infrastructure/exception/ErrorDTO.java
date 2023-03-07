package br.com.sergioluigi.githubuserprofilefindandkeeper.infrastructure.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO implements Serializable {

    private final LocalDateTime timestamp = LocalDateTime.now();

    private String path;

    private int status;

    private String error;

    private String message;

    private Map<String,String> validationErrors;

    private String requestId;


}
