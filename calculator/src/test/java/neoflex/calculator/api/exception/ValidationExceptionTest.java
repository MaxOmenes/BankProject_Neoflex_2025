package neoflex.calculator.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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