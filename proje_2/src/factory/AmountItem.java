package factory;

public class AmountItem implements IAmountItem {

    protected INamedItem item;
    protected double amount;
    public AmountItem(INamedItem item, double amount){
        this.item=item;
        this.amount=amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount=amount;
    }

    @Override
    public void increase(double amount){
        this.amount=this.amount+amount;
    }

    public String  toString(){
        StringBuilder sb=new StringBuilder();
        _toString(sb);
        return sb.toString();
    }
    protected void _toString(StringBuilder sb){
        sb.append(item.toString()+"\t"+amount);
    }

    @Override
    public int getId() {
        return item.getId();
    }

    @Override
    public String getName() {
        return item.getName();
    }
}
