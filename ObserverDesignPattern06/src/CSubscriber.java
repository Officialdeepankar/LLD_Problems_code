public class CSubscriber implements Isubscriber{


    public String nameofsubscriber;
    public CChannel c;

    public CSubscriber(String nameofsubscriber, CChannel c) {
        this.nameofsubscriber = nameofsubscriber;
        this.c = c;
    }

    @Override
    public void update() {
      c.getlatestvideotitle();
    }
}
