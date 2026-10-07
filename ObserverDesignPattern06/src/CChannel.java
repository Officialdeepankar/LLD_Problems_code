import java.util.ArrayList;
import java.util.List;

public class CChannel implements  IChannel{

    String nameofchannel;
    String latestVideoTitle;
    public List<Isubscriber> isubscriberList=new ArrayList<>();

    @Override
    public void subscribe(Isubscriber s) {
        isubscriberList.add(s);
    }

    @Override
    public void unsubscribe(Isubscriber s) {
       isubscriberList.remove(s);
    }

    public CChannel(String nameofchannel) {
        this.nameofchannel = nameofchannel;
    }

    @Override
    public void Notify() {
      // notfiy will iterate over subscriber list and calls update method of it .

        for(Isubscriber s:isubscriberList)
        {
            s.update();
        }
    }


    // Here we will make one method called upload-> which basically calls internally notfiy .

    public void uploadVideo(String Title)
    {
        // it will update latestvideotitle string and then call notify ;
        this.latestVideoTitle=Title;

        // calling notify

        Notify();

    }

    // here i am going to make one method called getVideotitle
    // it will simply return the latest title

    public void getlatestvideotitle()
    {
        System.out.println("Latest video title of : "+ nameofchannel + "is :"+ latestVideoTitle);
    }
}
