package factory;

import java.util.NoSuchElementException;

public class WorkOrder extends AmountItem implements IStateItem {
    private Product product;
    private int state;
    public WorkOrder(int id, String name, int idProduct, double amount){
        super(new NamedItem(id,name),amount);
        state=-1;
        product=Factory.getInstance().getProduct(idProduct);
    }
    public Product getProduct(){   
        return product;
    }
    public int getProductId(){
    	return product.getId();
    }
    public String getProductName(){
    	return product.getName();
    }

    public String toString(){
        StringBuilder sb=new StringBuilder();
        _toString(sb);
        sb.append("\t"+product.getId()+"\t"+product.getName()+"\n");
        return sb.toString();
    }

    public void upgradeState(){
        if(state==-1){
            // STORE reduce raw material from stock
            for(int i=0;i<product.getRawMaterialCount();i++) {
                StockRawMaterial rawMaterial = product.getRawMaterial(i);
                Factory.getInstance().increaseStock(Factory._RAWMATERIAL, rawMaterial.getId(), -1 *rawMaterial.getAmount()* this.getAmount());
            }
            state = 0;
        }else if(state<product.getProcessCount()){
            //Process steps
            state++;
        }else if(state==product.getProcessCount()){
            //Quality control

            //ProductStockRawMaterial rawMaterial = product.getRawMaterial(i);
            Factory.getInstance().increaseStock(Factory._PRODUCT, product.getId(), 1 * this.getAmount());

            state++;
        }else{
            //Illegal state
            throw new NoSuchElementException("State is beyond limit");
        }
    }
    public int getState(){
        return state;
    }


}
