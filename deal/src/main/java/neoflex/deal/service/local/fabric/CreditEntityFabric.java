package neoflex.deal.service.local.fabric;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neoflex.deal.api.dto.CreditDto;
import neoflex.deal.service.local.mapper.CreditMapper;
import neoflex.deal.store.entity.CreditEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreditEntityFabric {
    private final CreditMapper creditMapper;
    public CreditEntity create(CreditDto creditDto) {
        CreditEntity credit = creditMapper.toEntity(creditDto);
        log.info("CreditEntityFabric: Created CreditEntity from CreditDto: {}", credit);
        return credit;
    }
}
