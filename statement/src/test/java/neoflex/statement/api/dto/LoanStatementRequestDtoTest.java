package neoflex.statement.api.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanStatementRequestDtoTest {

    @Test
    void isAdult() {
        LoanStatementRequestDto build = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.now().minusYears(19))
                .build();
        assertTrue(build.isAdult(), "Expected isAdult to return true for age 18 or older");
    }

    @Test
    void isNotAdult() {
        LoanStatementRequestDto build = LoanStatementRequestDto.builder()
                .birthdate(LocalDate.now().minusYears(17))
                .build();
        assertFalse(build.isAdult(), "Expected isAdult to return false for age under 18");
    }
}