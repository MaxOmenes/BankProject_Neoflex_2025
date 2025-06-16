package neoflex.calculator.store.entity.offer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import neoflex.calculator.configuration.ConstantsConfiguration;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
public class OfferEntity implements Cloneable{
    private UUID statementId;
    private BigDecimal requestedAmount;
    private BigDecimal totalAmount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public OfferEntity clone() {
        try {
            OfferEntity clone = (OfferEntity) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public static class Builder {
        private final OfferEntity offerEntity = new OfferEntity();

        private static Double insuranceRate = ConstantsConfiguration.insuranceRate;
        private static Double salaryClientRate = ConstantsConfiguration.salaryClientRate;

        public Builder statementId(UUID statementId) {
            offerEntity.statementId = statementId;
            return this;
        }

        public Builder requestedAmount(BigDecimal requestedAmount) {
            offerEntity.requestedAmount = requestedAmount;
            return this;
        }

        public Builder totalAmount(BigDecimal totalAmount) {
            offerEntity.totalAmount = totalAmount;
            return this;
        }

        public Builder term(Integer term) {
            offerEntity.term = term;
            return this;
        }

        public Builder monthlyPayment(BigDecimal monthlyPayment) {
            offerEntity.monthlyPayment = monthlyPayment;
            return this;
        }

        public Builder rate(BigDecimal rate) {
            offerEntity.rate = offerEntity.rate.add(rate);
            return this;
        }

        public Builder isInsuranceEnabled(Boolean isInsuranceEnabled) {
            if (isInsuranceEnabled) {
                offerEntity.rate = offerEntity.rate.subtract(
                        BigDecimal.valueOf(insuranceRate)
                );
            }
            offerEntity.isInsuranceEnabled = isInsuranceEnabled;
            return this;
        }

        public Builder isSalaryClient(Boolean isSalaryClient) {
            if (isSalaryClient) {
                offerEntity.rate = offerEntity.rate.subtract(
                        BigDecimal.valueOf(salaryClientRate)
                );
            }
            offerEntity.isSalaryClient = isSalaryClient;
            return this;
        }

        public OfferEntity build() {
            OfferEntity clone = offerEntity.clone();
            return clone;
        }

        private Builder() {
            offerEntity.rate = BigDecimal.ZERO;
        }

        //METHODS USE ONLY FOR TESTING
        //FIXME: CHANGE IT
        @Deprecated
        private Builder setInsuranceRate(Double insuranceRate) {
            this.insuranceRate = insuranceRate;
            return this;
        }
        @Deprecated
        private Builder setSalaryClientRate(Double salaryClientRate) {
            this.salaryClientRate = salaryClientRate;
            return this;
        }
    }


}
