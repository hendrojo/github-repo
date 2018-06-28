package roman.convert.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String httpBadRequest(Exception ex,HttpServletRequest request) {
        return "redirect:/400";
    }
    
    @ExceptionHandler(Exception.class)
    public String internalServerError(Exception ex,HttpServletRequest request) {
    	return "redirect:/500";
    }
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public String httpNotFound(Exception ex,HttpServletRequest request) {
    	return "redirect:/404";
    }
}
