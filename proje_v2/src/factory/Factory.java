package factory;

import java.util.HashMap;

public class Factory {
    public static final String[] headerLib={"Raw Material", "Process", "Work Shop", "Supplier", "Customer"};
    public static final String[] headerMain={"Product", "Work Order", "Sales Order", "Purchase Order"};
    //private HashMap<Integer, INamedItem>[] main;
    private HashMap<Integer, INamedItem>[] lib;
    private HashMap<Integer, Product> products;
    private HashMap<Integer, WorkOrder> workOrders;
    public static final String[] headerStore={"Raw Material", "Product"};
    private HashMap<Integer, IAmountItem>[] stock;
    private HashMap<Integer, SalesOrder> salesOrders;
    private HashMap<Integer, PurchaseOrder> purchaseOrders;

    private static Factory singleton;
    public static final int _RAWMATERIAL=0;
    public static final int _PROCESS=1;
    public static final int _WORKSHOP=2;
    public static final int _SUPPLIER=3;
    public static final int _CUSTOMER=4;
    public static final int _PRODUCT=1;

    private Factory(){
        lib=new HashMap[headerLib.length];
        for(int i=0;i<lib.length;i++){
            lib[i]=new HashMap<Integer, INamedItem>();
        }
        
        stock =new HashMap[headerStore.length];
        for(int i=0;i<headerStore.length;i++){
            stock[i]=new HashMap<Integer, IAmountItem>();
        }
        products=new HashMap<Integer, Product>();
        workOrders=new HashMap<Integer, WorkOrder>();
        salesOrders= new HashMap<Integer, SalesOrder>();
        purchaseOrders=new HashMap<Integer, PurchaseOrder>();
        
    }

    public static Factory getInstance(){
        if(singleton==null){
            singleton =new Factory();
        }
        return singleton;
    }
   
    public HashMap<Integer, INamedItem> getLib(int type){
    	return lib[type];
    }
   
    
    public INamedItem getItem(int type, int id){
        return lib[type].get(id);
    }
    public void addItem(int type, INamedItem iname){
        lib[type].put(iname.getId(),iname);
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        _toString(sb);
        return sb.toString();
    }
    protected void _toString(StringBuilder sb){
        for(int i=0;i<lib.length;i++) {
            sb.append(headerLib[i] + ":" + "\n");
            for (int key : lib[i].keySet()) {
                INamedItem item = lib[i].get(key);
                sb.append(item.toString() + "\n");
            }
            sb.append("\n");
        }
        sb.append("Products:\n");
        for(int key: products.keySet()){
            sb.append(products.get(key));
        }
        sb.append("Work Orders:\n");
        for(int key: workOrders.keySet()){
            sb.append(workOrders.get(key));
        }
        for(int i = 0; i< stock.length; i++) {
            sb.append("Stock "+headerStore[i] + ":" + "\n");
            for (int key : stock[i].keySet()) {
                IAmountItem item = stock[i].get(key);
                sb.append(item.getId()+"\t"+item.getName()+"\t"+item.getAmount() + "\n");
            }
            sb.append("\n");
        }
    }
    public void show(){
        System.out.print(toString());
    }
    public void addProduct(Product product){
        products.put(product.getId(),product);
    }
    public Product getProduct(int id){
        return products.get(id);
    }
    public HashMap<Integer, Product> getProductList(){
    	return products;
    }

    public void addWorkOrder(WorkOrder wo){
       workOrders.put(wo.getId(),  wo);
    }
    public WorkOrder getWorkOrder(int id){
        return workOrders.get(id);
    }
    public HashMap<Integer, WorkOrder> getWorkOrderList(){
    	return workOrders;
    }

    public void addSalesOrder(SalesOrder so){
        salesOrders.put(so.getId(),  so);
    }
    public HashMap<Integer, SalesOrder> getSalesOrderList(){
    	return salesOrders;
    }
    public void addPurchaseOrder(PurchaseOrder po){
        purchaseOrders.put(po.getId(),  po);
    }
    public HashMap<Integer, PurchaseOrder> getPurchaseOrderList(){
    	return purchaseOrders;
    }

    public void increaseStock(int type, int id, double count){
        if(stock[type].containsKey(id)){
            stock[type].get(id).increase(count);
        }else{
            if(type==Factory._RAWMATERIAL){
                INamedItem rawMaterial=this.getItem(type,id);
                IAmountItem item= new AmountItem(rawMaterial,count);
                stock[type].put(id,item);
            }else if(type== Factory._PRODUCT){
                Product product=this.getProduct(id);
                IAmountItem item= new AmountItem(product,count);
                stock[type].put(id,item);
            }
        }
    }

}
