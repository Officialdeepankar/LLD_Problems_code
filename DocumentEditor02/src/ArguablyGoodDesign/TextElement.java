package ArguablyGoodDesign;

public class TextElement extends DocumentElement{
    String textelement="";

    public String getTextelement() {
        return textelement;
    }

    public void setTextelement(String textelement) {
        this.textelement = textelement;
    }

    public TextElement(String textelement) {
        this.textelement = textelement;
    }

    @Override
    public  String  render() {
        return textelement;
    }
}
