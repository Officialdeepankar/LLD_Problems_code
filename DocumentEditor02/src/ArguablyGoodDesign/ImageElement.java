package ArguablyGoodDesign;

public class ImageElement extends DocumentElement{

    String Imageaddress="";

    public String getImageaddress() {
        return Imageaddress;
    }

    public void setImageaddress(String imageaddress) {
        Imageaddress = imageaddress;
    }

    public ImageElement(String imageaddress) {
        Imageaddress = imageaddress;
    }

    @Override
    public String render() {
        return Imageaddress;
    }
}
