package factory;

import java.util.ArrayList;

public class SalesOrder extends NamedItem implements IStateItem {
    private INamedItem customer;
    private ArrayList<IAmountItem> products;
    private int state;
    public SalesOrder(int id, String name, int idCustomer, ArrayList<IAmountItem> products) {
        super(id, name);
        this.products=products;
        customer=Factory.getInstance().getItem(Factory._CUSTOMER,idCustomer);
        state=0;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void upgradeState() {
        if(state==0){
            for(int i=0;i<products.size();i++){
                IAmountItem product=products.get(i);
                Factory.getInstance().increaseStock(Factory._PRODUCT,product.getId(),-1*product.getAmount());
            }
            state=1;
        }
    }
}
