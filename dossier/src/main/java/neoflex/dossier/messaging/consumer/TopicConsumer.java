package neoflex.dossier.messaging.consumer;

public interface TopicConsumer<T> {
    void handle(T message);
}
