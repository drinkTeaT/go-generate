package club.tacbin.ggen.config;


import club.tacbin.ggen.dto.ResponseInfo;
import club.tacbin.ggen.dto.Status;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description :全局异常处理
 * @Author : Administrator
 * @Date : 2020-03-06 11:45
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseInfo handleError(Exception e) {
        e.printStackTrace();
        return new ResponseInfo(e.getMessage(), Status.FAIL, null);
    }
}
