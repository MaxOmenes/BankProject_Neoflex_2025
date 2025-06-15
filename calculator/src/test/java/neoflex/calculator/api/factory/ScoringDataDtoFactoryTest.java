package neoflex.calculator.api.factory;

import neoflex.calculator.api.dto.EmploymentDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;
import neoflex.calculator.store.entity.scoring.EmploymentEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class ScoringDataDtoFactoryTest {

    @Autowired
    private ScoringDataDtoFactory scoringDataDtoFactory;
    @Autowired
    private EmploymentDtoFactory employmentDtoFactory;

    @Test
    void toEntity() {
        ScoringDataDto dto = new ScoringDataDto();
        dto.setAmount(new BigDecimal("100000"));
        dto.setTerm(12);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setMiddleName("Smith");
        dto.setGender(Gender.MALE.name());
        dto.setBirthdate(LocalDate.of(1990, 1, 1));
        dto.setPassportSeries("1234");
        dto.setPassportNumber("567890");
        dto.setPassportIssueDate(LocalDate.of(2010, 1, 1));
        dto.setPassportIssueBranch("Branch123");
        dto.setMaritalStatus(MartialStatus.MARRIED.name());
        dto.setDependentAmount(2);

        EmploymentDto employmentDto = new EmploymentDto();
        dto.setEmployment(employmentDto);

        employmentDto.setEmploymentStatus(EmploymentStatus.EMPLOYED.toString());
        employmentDto.setEmployerINN("1234567890");
        employmentDto.setSalary(BigDecimal.valueOf(50000));
        employmentDto.setPosition(EmploymentPosition.MANAGER.toString());
        employmentDto.setWorkExperienceTotal(18);
        employmentDto.setWorkExperienceCurrent(12);



        // Act
        ScoringEntity result = scoringDataDtoFactory.toEntity(dto);

        // Assert
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getTerm(), result.getTerm());
        assertEquals(dto.getFirstName(), result.getFirstName());
        assertEquals(dto.getLastName(), result.getLastName());
        assertEquals(dto.getMiddleName(), result.getMiddleName());
        assertEquals(Gender.valueOf(dto.getGender()), result.getGender());
        assertEquals(dto.getBirthdate(), result.getBirthdate());
        assertEquals(dto.getPassportSeries(), result.getPassportSeries());
        assertEquals(dto.getPassportNumber(), result.getPassportNumber());
        assertEquals(dto.getPassportIssueDate(), result.getPassportIssueDate());
        assertEquals(dto.getPassportIssueBranch(), result.getPassportIssueBranch());
        assertEquals(MartialStatus.valueOf(dto.getMaritalStatus()), result.getMaritalStatus());
        assertEquals(dto.getDependentAmount(), result.getDependentAmount());
        assertEquals(dto.getAccountNumber(), result.getAccountNumber());
        assertEquals(dto.getIsInsuranceEnabled(), result.getIsInsuranceEnabled());
        assertEquals(dto.getIsSalaryClient(), result.getIsSalaryClient());
    }
}