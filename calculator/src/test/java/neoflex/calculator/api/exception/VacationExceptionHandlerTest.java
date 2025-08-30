package neoflex.calculator.api.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationExceptionHandlerTest {
    @InjectMocks
    private VacationExceptionHandler exceptionHandler;

    @Mock
    private WebRequest webRequest;

    @Test
    void exception_ShouldDelegateToParentHandler() throws Exception {
        // Arrange
        Exception testException = new RuntimeException("Test exception");
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        VacationExceptionHandler spyHandler = spy(exceptionHandler);
        doReturn(expectedResponse).when(spyHandler).handleException(any(), any());

        // Act
        ResponseEntity<Object> result = spyHandler.exception(testException, webRequest);

        // Assert
        assertEquals(expectedResponse, result);
        verify(spyHandler).handleException(testException, webRequest);
    }

    @Test
    void handleConstraintViolation_ShouldReturnBadRequestWithValidationErrors() {
        // Arrange
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        when(violation1.getMessage()).thenReturn("Error 1");

        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
        when(violation2.getMessage()).thenReturn("Error 2");

        violations.add(violation1);
        violations.add(violation2);

        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolation(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Validation Error", responseBody.get("error"));

        String errorMessage = (String) responseBody.get("message");
        assertTrue(errorMessage.contains("Error 1"));
        assertTrue(errorMessage.contains("Error 2"));
    }

    @Test
    void handleConstraintViolation_WithEmptyViolations_ShouldUseDefaultMessage() {
        // Arrange
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolationException exception = new ConstraintViolationException("Validation failed", violations);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleConstraintViolation(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Validation Error", responseBody.get("error"));
        assertEquals("Validation failed", responseBody.get("message"));
    }
}