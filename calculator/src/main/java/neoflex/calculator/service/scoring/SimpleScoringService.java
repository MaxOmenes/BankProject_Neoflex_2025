package neoflex.calculator.service.scoring;

import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.api.dto.CreditDto;
import neoflex.calculator.api.dto.ScoringDataDto;
import neoflex.calculator.store.entity.enums.EmploymentPosition;
import neoflex.calculator.store.entity.enums.EmploymentStatus;
import neoflex.calculator.store.entity.enums.Gender;
import neoflex.calculator.store.entity.enums.MartialStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
@Slf4j
public class SimpleScoringService implements ScoringService{
    @Value("${constants.rate}")
    Double rate;
    @Value("${constants.insuranceRate}")
    Double insuranceRate;
    @Value("${constants.salaryClientRate}")
    Double salaryClientRate;

    @Override
    public CreditDto score(ScoringDataDto score) {
        BigDecimal currentRate = BigDecimal.valueOf(rate);

        //TODO: !!!HOW I THINK!!! best practise for this is to use chain of responsibility pattern, but now I can`t make it, because I have exam in University :)
        if (score.getAmount().compareTo(BigDecimal.valueOf(20000)) < 0) {
            log.info("Refused scoring for amount less than 20000: {}", score.getAmount());
            return null;
        }

        if (score.getTerm() < 6) {
            log.info("Refused scoring for term less than 6 months: {}", score.getTerm());
            return null;
        }

        Integer age = Period.between(score.getBirthdate(), LocalDate.now()).getYears();

        if (age < 20 || age > 65) {
            log.info("Refused scoring for age not in range 20-65: {}", age);
            return null;
        }

        switch (EmploymentStatus.valueOf(score.getEmployment().getEmploymentStatus())){
            case UNEMPLOYED:
                log.info("Refused scoring for unemployed status");
                return null;
            case EMPLOYED:
                break;
            case SELF_EMPLOYED:
                currentRate = currentRate.add(BigDecimal.TWO);
                break;
            case BUSINESS_OWNER:
                currentRate = currentRate.add(BigDecimal.ONE);
                break;
            default:
                break;
        }

        switch (EmploymentPosition.valueOf(score.getEmployment().getPosition())){
            case MANAGER:
                break;
            case MIDDLE_MANAGER:
                currentRate = currentRate.subtract(BigDecimal.TWO);
                break;
            case TOP_MANAGER:
                currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                break;
            default:
                break;
        }

        if (score.getAmount().compareTo(
                score.getEmployment().getSalary().multiply(
                        BigDecimal.valueOf(24)
                )) > 0
        ){
            log.info("Refused scoring for amount more than 24 salaries: {}", score.getAmount());
            return null;
        }

        switch (MartialStatus.valueOf(score.getMaritalStatus())){
            case MARRIED:
                currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                break;
            case DIVORCED:
                currentRate = currentRate.add(BigDecimal.ONE);
                break;
            case SINGLE:
                break;
            default:
                break;
        }

        switch (Gender.valueOf(score.getGender())){
            case MALE:
                if (age >= 30 && age <= 55){
                    currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case FEMALE:
                if (age >= 32 && age <= 60){
                    currentRate = currentRate.subtract(BigDecimal.valueOf(3));
                }
                break;
            case OTHER:
                currentRate = currentRate.add(BigDecimal.valueOf(7));
                break;
            default:
                break;

        }

        if (score.getEmployment().getWorkExperienceTotal() < 18) {
            log.info("Refused scoring for total work experience less than 18 months: {}", score.getEmployment().getWorkExperienceTotal());
            return null;
        }

        if (score.getEmployment().getWorkExperienceCurrent() < 3) {
            log.info("Refused scoring for current work experience less than 3 months: {}", score.getEmployment().getWorkExperienceCurrent());
            return null;
        }

        if (score.getIsInsuranceEnabled()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(insuranceRate));
        }

        if (score.getIsSalaryClient()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(salaryClientRate));
        }

        return CreditDto.builder()
                .amount(score.getAmount())
                .term(score.getTerm())
                .rate(currentRate)
                .isInsuranceEnabled(score.getIsInsuranceEnabled())
                .isSalaryClient(score.getIsSalaryClient())
                .build();
    }
}
