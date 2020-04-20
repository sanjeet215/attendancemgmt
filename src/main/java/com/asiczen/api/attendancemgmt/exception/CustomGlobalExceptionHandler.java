package com.asiczen.api.attendancemgmt.exception;


import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	// error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("message", errors);

        return new ResponseEntity<>(body, headers, status);

    }
    
    // Exception Handler for Resource Not found.
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage(ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
    
    
    @ExceptionHandler(MyFileNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> FileNotFoundException(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        errors.setMessage(ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<CustomErrorResponse> resourceAlreadyExistException(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getLocalizedMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());
        errors.setMessage(ex.getLocalizedMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

    }
    
    
    
    
    
 // @Validate For Validating Path Variables and Request Parameters
//    @ExceptionHandler(ConstraintViolationException.class)
//    public void constraintViolationException(HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value());
//    }

    //ConstraintViolationException
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> constraintViolationException(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getCause().getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());
        errors.setMessage(ex.getMessage());
        
        
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> dataIntegrityViolationException(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getCause().getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());
        errors.setMessage(ex.getLocalizedMessage());
        
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<CustomErrorResponse> sqlException(Exception ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getCause().getMessage());
        errors.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);

    }
    
    @ExceptionHandler(SomeInternalException.class)
    public ResponseEntity<CustomErrorResponse> fileUploadException(Exception ex, WebRequest request){
    	
    	  CustomErrorResponse errors = new CustomErrorResponse();
          errors.setTimestamp(LocalDateTime.now());
          errors.setError(ex.getMessage());
          errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
          errors.setMessage(ex.getMessage());

          return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(StatusAlreadyApproved.class)
    public ResponseEntity<CustomErrorResponse> recordAlreadyApproved(Exception ex, WebRequest request){
    	
    	 CustomErrorResponse errors = new CustomErrorResponse();
         errors.setTimestamp(LocalDateTime.now());
         errors.setError(ex.getMessage());
         errors.setStatus(HttpStatus.CONFLICT.value());
         errors.setMessage(ex.getMessage());

         return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(ZipFileCreationException.class)
    public ResponseEntity<CustomErrorResponse> zipFileCreationException(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errors.setMessage(ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    
    
    @ExceptionHandler(UnauthorizedAccess.class)
    public ResponseEntity<CustomErrorResponse> unauthorizedAccessException(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.UNAUTHORIZED.value());
        errors.setMessage(ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> badRequestHandler(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(ex.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<CustomErrorResponse> invalidDateFormatException(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
    	errors.setTimestamp(LocalDateTime.now());
        errors.setError("Invalid date and time format");
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(ex.getMessage());
    	
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(DateFormatException.class)
    public ResponseEntity<CustomErrorResponse> dateFormatException(Exception ex, WebRequest request){
    	
    	CustomErrorResponse errors = new CustomErrorResponse();
    	errors.setTimestamp(LocalDateTime.now());
        errors.setError("Invalid date and time format");
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        errors.setMessage(ex.getMessage());
    	
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

