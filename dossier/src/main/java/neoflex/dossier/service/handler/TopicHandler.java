package neoflex.dossier.service.handler;

public interface TopicHandler<T> {
    void handle(T message);
}
