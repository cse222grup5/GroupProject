package factory;

public class StockRawMaterial extends AmountItem {

    public StockRawMaterial(int id,double amount){
        super(Factory.getInstance().getItem(Factory._RAWMATERIAL,id),amount);
    }
    public String toString(){
        return super.toString();
    }
}
