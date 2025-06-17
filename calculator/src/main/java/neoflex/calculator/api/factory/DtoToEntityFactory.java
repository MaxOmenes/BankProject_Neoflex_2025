package neoflex.calculator.api.factory;

public interface DtoToEntityFactory<D, E>{
     E toEntity(D dto);
}
