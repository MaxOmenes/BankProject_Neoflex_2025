package neoflex.calculator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
public class ConstantsConfiguration {
    @Value("${constants.insuranceRate}")
    public static Double insuranceRate;
    @Value("${constants.salaryClientRate}")
    public static Double salaryClientRate;
    @Value("${constants.insurancePolicyRate}")
    public static Double insurancePolicyRate;


    @Component
    private static class RateInitializer {
        @Value("${constants.insuranceRate}")
        private Double insuranceRateValue;

        @Value("${constants.salaryClientRate}")
        private Double salaryClientRateValue;

        @Value("${constants.insurancePolicyRate}")
        private static Double insurancePolicyRateValue;

        @PostConstruct
        public void init() {
            insuranceRate = insuranceRateValue;
            salaryClientRate = salaryClientRateValue;
            insurancePolicyRate = insurancePolicyRateValue;
        }
    }
}
