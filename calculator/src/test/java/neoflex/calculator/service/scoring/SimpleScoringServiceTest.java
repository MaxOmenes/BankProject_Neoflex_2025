package neoflex.calculator.service.scoring;

import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.EmploymentDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SimpleScoringServiceTest {
    @Autowired
    SimpleScoringService simpleScoringService;

    BigDecimal EPSILON = BigDecimal.valueOf(1);
    ScoringDataDto scoringNormal;
    EmploymentDto employmentNormal;
    @BeforeEach
    void setUp() {
        employmentNormal = EmploymentDto.builder()
                .employmentStatus(String.valueOf(EmploymentStatus.EMPLOYED))
                .employerINN("123456")
                .salary(BigDecimal.valueOf(50000))
                .position(String.valueOf(EmploymentPosition.MANAGER))
                .workExperienceTotal(18)
                .workExperienceCurrent(24)
                .build();

        scoringNormal = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(36)
                .firstName("John")
                .lastName("Doe")
                .middleName("Smith")
                .gender(String.valueOf(Gender.MALE))
                .birthdate(LocalDate.of(1990, 1, 1))
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueDate(LocalDate.of(2010, 1, 1))
                .passportIssueBranch("Main Branch")
                .maritalStatus(String.valueOf(MartialStatus.MARRIED))
                .dependentAmount(2)
                .employment(employmentNormal)
                .accountNumber("1234567890")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }


    @Test
    void score() {
        CreditDto score = simpleScoringService.score(scoringNormal);

        assertTrue(score.getRate().equals(BigDecimal.valueOf(26.5)));
    }

    @Test
    void scoreWithUnemployed() {
        employmentNormal.setEmploymentStatus(String.valueOf(EmploymentStatus.UNEMPLOYED));

        scoringNormal.setEmployment(employmentNormal);


        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for unemployed status");
    }
    @Test
    void scoreWithLittleAge(){
        scoringNormal.setBirthdate(LocalDate.of(2010, 1, 1)); // Set age to 13

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for age less than 20");
    }

    @Test
    void scoreWithHighAge(){
        scoringNormal.setBirthdate(LocalDate.of(1950, 1, 1)); // Set age to 73

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for age more than 65");
    }

    @Test
    void scoreWithUnder24Salaries() {
        scoringNormal.setAmount(BigDecimal.valueOf(999999999));

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for amount more than 24 salaries");
    }

    @Test
    void scoreWithShortTerm() {
        scoringNormal.setTerm(3); // Set term to 3 months

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for term less than 6 months");
    }

    @Test
    void scoreWithLowAmount() {
        scoringNormal.setAmount(BigDecimal.valueOf(15000)); // Set amount to 15000

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for amount less than 20000");
    }

    @Test
    void scoreWithHighAmount() {
        scoringNormal.setAmount(BigDecimal.valueOf(1000000)); // Set amount to 1,000,000

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(26.5), "Expected rate for high amount");
    }

    @Test
    void scoreWithSelfEmployed() {
        employmentNormal.setEmploymentStatus(String.valueOf(EmploymentStatus.SELF_EMPLOYED));
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate for self-employed status");
    }

    @Test
    void scoreWithBusinessOwner() {
        employmentNormal.setEmploymentStatus(String.valueOf(EmploymentStatus.BUSINESS_OWNER));
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(27.5), "Expected rate for business owner status");
    }

    @Test
    void scoreWithMiddleManager() {
        employmentNormal.setPosition(String.valueOf(EmploymentPosition.MIDDLE_MANAGER));
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(24.5), "Expected rate for middle manager position");
    }

    @Test
    void scoreWithTopManager() {
        employmentNormal.setPosition(String.valueOf(EmploymentPosition.TOP_MANAGER));
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(23.5), "Expected rate for top manager position");
    }

    @Test
    void scoreWithDivorced() {
        scoringNormal.setMaritalStatus(String.valueOf(MartialStatus.DIVORCED));

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(30.5), "Expected rate for divorced marital status");
    }

    @Test
    void scoreWithSingle() {
        scoringNormal.setMaritalStatus(String.valueOf(MartialStatus.SINGLE));

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate for single marital status");
    }

    @Test
    void scoreWithMaleAge30To55() {
        scoringNormal.setBirthdate(LocalDate.of(1985, 1, 1)); // Set age to 40

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(26.5), "Expected rate");
    }

    @Test
    void scoreWithMaleAgeUnder30() {
        scoringNormal.setBirthdate(LocalDate.of(2000, 1, 1)); // Set age to 25

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate");
    }

    @Test
    void scoreWithMaleAgeOver55() {
        scoringNormal.setBirthdate(LocalDate.of(1960, 1, 1)); // Set age to 65

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate");
    }

    @Test
    void scoreWithFemaleAge30To55() {
        scoringNormal.setBirthdate(LocalDate.of(1985, 1, 1)); // Set age to 40
        scoringNormal.setGender(String.valueOf(Gender.FEMALE));
        CreditDto score = simpleScoringService.score(scoringNormal);
        assertEquals(score.getRate(), BigDecimal.valueOf(26.5), "Expected rate");
    }

    @Test
    void scoreWithFemaleAgeUnder30() {
        scoringNormal.setBirthdate(LocalDate.of(2000, 1, 1)); // Set age to 25
        scoringNormal.setGender(String.valueOf(Gender.FEMALE));
        CreditDto score = simpleScoringService.score(scoringNormal);
        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate");
    }
    @Test
    void scoreWithFemaleAgeOver55() {
        scoringNormal.setBirthdate(LocalDate.of(1960, 1, 1)); // Set age to 65
        scoringNormal.setGender(String.valueOf(Gender.FEMALE));
        CreditDto score = simpleScoringService.score(scoringNormal);
        assertEquals(score.getRate(), BigDecimal.valueOf(29.5), "Expected rate");
    }

    @Test
    void scoreWithOtherGender() {
        scoringNormal.setGender(String.valueOf(Gender.OTHER));
        CreditDto score = simpleScoringService.score(scoringNormal);
        assertEquals(score.getRate(), BigDecimal.valueOf(36.5), "Expected rate");
    }
    @Test
    void scoreWithNotSalaryClient() {
        scoringNormal.setIsSalaryClient(false);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(28.0), "Expected rate for not salary client");
    }

    @Test
    void scoreWithInsuranceDisabled() {
        scoringNormal.setIsInsuranceEnabled(false);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertEquals(score.getRate(), BigDecimal.valueOf(28.5), "Expected rate for insurance disabled");
    }

    @Test
    void scoreWithLessThan18MonthsExperience() {
        employmentNormal.setWorkExperienceTotal(17); // Set total work experience to 17 months
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for total work experience less than 18 months");
    }

    @Test
    void scoreWithLessThan3MonthsCurrentExperience() {
        employmentNormal.setWorkExperienceCurrent(2); // Set current work experience to 2 months
        scoringNormal.setEmployment(employmentNormal);

        CreditDto score = simpleScoringService.score(scoringNormal);

        assertNull(score, "Expected null for current work experience less than 3 months");
    }


}