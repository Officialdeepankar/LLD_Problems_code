package ArguablyGoodDesign;

import java.util.ArrayList;
import java.util.List;

public   class Document {

    public List<DocumentElement> document=new ArrayList<>();


    public  String render()
    {
        String result="";
        for (DocumentElement ele:document)
        {
            result+=ele.render();//delegating work to document element render function.
        }

        return result;

    };


    public void addelement(DocumentElement ele)
    {
        document.add(ele);
    }

    public void removeElement(DocumentElement ele)
    {
        document.remove(ele);
    }


}
