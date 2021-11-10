package club.tacbin.ggen.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Getter
@Setter
@Builder
public class ResponseInfo<T> implements Serializable {
    private String message;

    private Status status;

    private T data;

    public ResponseInfo(String errorMessage, Status status, T data) {
        this.message = errorMessage;
        this.status = status;
        this.data = data;
    }
}
