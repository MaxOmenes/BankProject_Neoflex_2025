package neoflex.deal.messaging.producer;

public interface TopicProducer<T> {
    void send(T message);
}
