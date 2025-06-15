package neoflex.calculator.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ValidationExceptionTest {
    @Test
    void testValidationException() {
        // Given
        String errorMessage = "Invalid input data";

        // When
        ValidationException exception = new ValidationException(errorMessage);

        // Then
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

}