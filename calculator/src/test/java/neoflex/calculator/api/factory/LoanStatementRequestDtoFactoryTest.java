package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.LoanStatementRequestDto;
import neoflex.calculator.store.entity.offer.LoanStatementRequestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoanStatementRequestDtoFactoryTest {
    @Autowired
    private LoanStatementRequestDtoFactory factory;

    @Test
    void toEntity() {
        LoanStatementRequestDto dto = LoanStatementRequestDto.builder()
                .amount(new BigDecimal("100000"))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .middleName("Smith")
                .email("john.doe@example.com")
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .build();

        // Act
        LoanStatementRequestEntity result = factory.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertNull(result.getId()); // ID should be null
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getMiddleName(), result.getMiddleName());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getBirthdate(), result.getBirthdate());
        assertEquals(dto.getPassportSeries(), result.getPassportSeries());
        assertEquals(dto.getPassportNumber(), result.getPassportNumber());
    }
}