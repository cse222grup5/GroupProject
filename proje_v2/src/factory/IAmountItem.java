package factory;

public interface IAmountItem extends INamedItem {
    //public IIndexed getItem();
    public double getAmount();
    public void setAmount(double Amount);
    public void increase(double Amount);
}
