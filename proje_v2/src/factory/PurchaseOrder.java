package factory;

import java.util.ArrayList;

public class PurchaseOrder extends NamedItem implements IStateItem {
    private INamedItem supplier;
    private ArrayList<IAmountItem> rawMaterials;
    private int state;
    public PurchaseOrder(int id, String name, int idSupplier, ArrayList<IAmountItem> rawMaterials) {
        super(id, name);
        this.rawMaterials=rawMaterials;
        supplier=Factory.getInstance().getItem(Factory._SUPPLIER,idSupplier);
        state=0;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void upgradeState() {
        if(state==0){
            for(int i=0;i<rawMaterials.size();i++){
                IAmountItem rm=rawMaterials.get(i);
                Factory.getInstance().increaseStock(Factory._RAWMATERIAL,rm.getId(),-1*rm.getAmount());
            }
            state=1;
        }
    }
}