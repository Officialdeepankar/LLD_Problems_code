//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        CChannel DeepTalk=new CChannel("DeepTalk");

        // two subscribers have subscribed to the channell
        Isubscriber sarthak=new CSubscriber("Sarthak",DeepTalk);
        Isubscriber Swapnil=new CSubscriber("Swapnil",DeepTalk);

        // lets add them to the deeptalk subscriber list .

        DeepTalk.subscribe(sarthak);
        DeepTalk.subscribe(Swapnil);

        // we will upload one video with title "meri pheli video"

        DeepTalk.uploadVideo("Meri pheli video");

        // then upload video will internally call notify and will iterate over list and
        // trigger update method of each, which will inteanlly call getvideotitel from
        // CChannel class

        // and in terminal it should show message two tiems

    }
}