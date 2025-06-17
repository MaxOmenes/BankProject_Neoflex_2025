package neoflex.calculator.api.factory;

public interface EntityToDtoFactory <D, E>{
    D toDto(E entity);
}
