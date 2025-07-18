package neoflex.statement.api.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void exception_shouldReturnInternalServerError() {
        // Arrange
        Exception testException = new RuntimeException("Test error message");

        // Act
        ResponseEntity<Object> response = globalExceptionHandler.exception(testException);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred. Please try again later.", response.getBody());
    }

    @Test
    void handleValidationException_shouldReturnBadRequest() {
        // Arrange
        FieldError fieldError1 = new FieldError("objectName", "field1", "Field1 is required");
        FieldError fieldError2 = new FieldError("objectName", "field2", "Field2 must be valid");

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Act
        ResponseEntity<Object> response = globalExceptionHandler.handleValidationException(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Validation failed:"));
        assertTrue(response.getBody().toString().contains("Field1 is required"));
        assertTrue(response.getBody().toString().contains("Field2 must be valid"));
    }

    @Test
    void handleValidationException_withNoErrors_shouldReturnDefaultMessage() {
        // Arrange
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of());

        // Act
        ResponseEntity<Object> response = globalExceptionHandler.handleValidationException(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed: Invalid input", response.getBody());
    }

    @Test
    void handleValidationException_withSingleError_shouldReturnSingleMessage() {
        // Arrange
        FieldError fieldError = new FieldError("objectName", "field", "Field is required");

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError));

        // Act
        ResponseEntity<Object> response = globalExceptionHandler.handleValidationException(methodArgumentNotValidException);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation failed: Field is required", response.getBody());
    }
}