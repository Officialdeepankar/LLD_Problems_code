package BadDesign01;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.*;

public class DocumentEditor {
    /*
       Here we are saying that a document editor can take text or image for now but should be scalable i.e in future it can
       support , videos , tables, fonts tabspace etc.

     */

     List<String> elements=new ArrayList<>() ;// elements here means text, or image paths


    //Methods
    //1) We can add text  ,so there will be one add text method
     public  void addText(String s)
     {
         elements.add(s);
     }


    // 2) We can add image , so there will be one add image method


     public void addimage(String imageaddress)
     {
         elements.add(imageaddress);
     }

    // 3) We will give a render document button to the user, by clicking at which he will see the rendered doc in teh
    // console

    public String renderDoc()
    {
        String ans="";
        for (String s: elements)
        {
            ans+=s;
        }
        return ans;
    }



    // 4) SavetofileMethod(), using this method user will be able to save the rendered document in a file.


    public void saveTofile()
    {
        Path filePath = Paths.get("example.txt");
        String content=renderDoc();

        try{
            Files.writeString(filePath,content);
            System.out.println("File created and written successfully.");
        }catch (Exception e)
        {
            System.out.println(e);
        }



    }


}
