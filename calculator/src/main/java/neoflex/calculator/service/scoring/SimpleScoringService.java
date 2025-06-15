package neoflex.calculator.service.scoring;

import lombok.extern.slf4j.Slf4j;
import neoflex.calculator.store.entity.credit.CreditEntity;
import neoflex.calculator.store.entity.scoring.ScoringEntity;
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
    public CreditEntity score(ScoringEntity entity) {
        BigDecimal currentRate = BigDecimal.valueOf(rate);

        //TODO: !!!HOW I THINK!!! best practise for this is to use chain of responsibility pattern, but now I can`t make it, because I have exam in University :)
        if (entity.getAmount().compareTo(BigDecimal.valueOf(20000)) < 0) {
            log.info("Refused scoring for amount less than 20000: {}", entity.getAmount());
            return null;
        }

        if (entity.getTerm() < 6) {
            log.info("Refused scoring for term less than 6 months: {}", entity.getTerm());
            return null;
        }

        Integer age = Period.between(entity.getBirthdate(), LocalDate.now()).getYears();

        if (age < 20 || age > 65) {
            log.info("Refused scoring for age not in range 20-65: {}", age);
            return null;
        }

        switch (entity.getEmployment().getEmploymentStatus()){
            case UNEMPLOYED:
                log.info("Refused scoring for unemployed status");
                return null;
            case EMPLOYED:
                break;
            case SELF_EMPLOYED:
                currentRate = currentRate.add(BigDecimal.TWO);
            case BUSINESS_OWNER:
                currentRate = currentRate.add(BigDecimal.ONE);
                break;
            default:
                break;
        }

        switch (entity.getEmployment().getPosition()){
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

        if (entity.getAmount().compareTo(
                entity.getEmployment().getSalary().multiply(
                        BigDecimal.valueOf(24)
                )) > 0
        ){
            log.info("Refused scoring for amount more than 24 salaries: {}", entity.getAmount());
            return null;
        }

        switch (entity.getMaritalStatus()){
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

        switch (entity.getGender()){
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

        if (entity.getEmployment().getWorkExperienceTotal() < 18) {
            log.info("Refused scoring for total work experience less than 18 months: {}", entity.getEmployment().getWorkExperienceTotal());
            return null;
        }

        if (entity.getEmployment().getWorkExperienceCurrent() < 3) {
            log.info("Refused scoring for current work experience less than 3 months: {}", entity.getEmployment().getWorkExperienceCurrent());
            return null;
        }

        if (entity.getIsInsuranceEnabled()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(insuranceRate));
        }

        if (entity.getIsSalaryClient()) {
            currentRate = currentRate.subtract(BigDecimal.valueOf(salaryClientRate));
        }

        return CreditEntity.builder()
                .amount(entity.getAmount())
                .term(entity.getTerm())
                .rate(currentRate)
                .isInsuranceEnabled(entity.getIsInsuranceEnabled())
                .isSalaryClient(entity.getIsSalaryClient())
                .build();
    }
}
