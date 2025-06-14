package neoflex.calculator.api.factory;

public interface EntityToDtoFactory <D, E>{
    public D toDto(E entity);
}
