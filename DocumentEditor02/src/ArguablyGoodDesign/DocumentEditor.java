package ArguablyGoodDesign;

public  class DocumentEditor {

    Document doc=new Document();

    PersistInDb persistInDb=new SaveToFile();

     void addImage(String addressofimg)
     {
        DocumentElement imageElement=new ImageElement(addressofimg);
        doc.addelement(imageElement);
     }


     void addText(String text)
     {
         DocumentElement textElement=new TextElement(text);
         doc.addelement(textElement);
     }

     String render()
     {
        String result= doc.render();// delegate the call to rnder of document and will delgate it to document element render function

        return result;
     }

     void save()
     {
         String wholecontent=render();
         persistInDb.save(wholecontent);
     }

}
