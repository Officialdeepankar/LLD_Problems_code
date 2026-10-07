public interface IChannel {

    void subscribe(Isubscriber s);
    void unsubscribe(Isubscriber s);
    void Notify();
}
