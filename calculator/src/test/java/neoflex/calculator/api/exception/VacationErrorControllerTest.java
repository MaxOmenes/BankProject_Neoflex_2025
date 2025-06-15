package neoflex.calculator.api.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacationErrorControllerTest {
    @Mock
    private ErrorAttributes errorAttributes;

    @Mock
    private WebRequest webRequest;

    @InjectMocks
    private VacationErrorController controller;

    @Test
    void error_WithValidStatus_ShouldReturnResponseWithSameStatus() {
        // Arrange
        Map<String, Object> errorAttributesMap = new HashMap<>();
        errorAttributesMap.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributesMap.put("error", "Bad Request");
        errorAttributesMap.put("message", "Invalid input");

        when(errorAttributes.getErrorAttributes(eq(webRequest), any(ErrorAttributeOptions.class)))
                .thenReturn(errorAttributesMap);

        // Act
        ResponseEntity<Object> response = controller.error(webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDTO errorDTO = (ErrorDTO) response.getBody();
        assertNotNull(errorDTO);
        assertEquals("Bad Request", errorDTO.getError());
        assertEquals("Invalid input", errorDTO.getDescription());
    }

    @Test
    void error_WithNullStatus_ShouldReturnInternalServerError() {
        // Arrange
        Map<String, Object> errorAttributesMap = new HashMap<>();
        errorAttributesMap.put("status", null);
        errorAttributesMap.put("error", "Server Error");
        errorAttributesMap.put("message", "Something went wrong");

        when(errorAttributes.getErrorAttributes(eq(webRequest), any(ErrorAttributeOptions.class)))
                .thenReturn(errorAttributesMap);

        // Act
        ResponseEntity<Object> response = controller.error(webRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorDTO errorDTO = (ErrorDTO) response.getBody();
        assertNotNull(errorDTO);
        assertEquals("Server Error", errorDTO.getError());
        assertEquals("Something went wrong", errorDTO.getDescription());
    }
}