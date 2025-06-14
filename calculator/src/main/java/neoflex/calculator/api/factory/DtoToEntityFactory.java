package neoflex.calculator.api.factory;

public interface DtoToEntityFactory<D, E>{
     public E toEntity(D dto);
}
