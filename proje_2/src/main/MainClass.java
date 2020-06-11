package main;

import factory.*;
import gui.gui;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] ar){

        System.out.println("started");
        Factory fac=Factory.getInstance();
        fac.addItem(Factory._RAWMATERIAL,new NamedItem(1001,"rm1"));
        fac.addItem(Factory._RAWMATERIAL,new NamedItem(1002,"rm2"));

        fac.addItem(Factory._PROCESS,new NamedItem(1001,"pr1"));
        fac.addItem(Factory._PROCESS,new NamedItem(1002,"pr2"));

        fac.addItem(Factory._WORKSHOP,new NamedItem(1001,"ws1"));
        fac.addItem(Factory._WORKSHOP,new NamedItem(1002,"ws2"));

        fac.addItem(Factory._CUSTOMER,new NamedItem(1001,"cus1"));
        fac.addItem(Factory._CUSTOMER,new NamedItem(1002,"cus2"));

        ArrayList<StockRawMaterial> rm=new ArrayList<StockRawMaterial>();
        rm.add(new StockRawMaterial(1001,0.1));
        rm.add(new StockRawMaterial(1002,0.01));
        ArrayList<ProcessWorkShopPair> pr=new ArrayList<ProcessWorkShopPair>();
        pr.add(new ProcessWorkShopPair(1001,1001));
        pr.add(new ProcessWorkShopPair(1002,1002));
        Product pr1=new Product(101,"pr1",rm,pr);
        fac.addProduct(pr1);

        WorkOrder wo=new WorkOrder(1001,"wo1001",101,10);
        fac.addWorkOrder(wo);

        fac.increaseStock(0,1001,77);
        fac.increaseStock(0,1002,55);

        fac.increaseStock(1,101,33);

        wo.upgradeState();
        wo.upgradeState();
        wo.upgradeState();
        wo.upgradeState();

        ArrayList<IAmountItem> products=new ArrayList<IAmountItem>();
        //INamedItem prd=Factory.getInstance().getProduct(101);
        products.add(new AmountItem(Factory.getInstance().getProduct(101),23));
        SalesOrder so=new SalesOrder(1001,"so1",1001,products);
        so.upgradeState();


        fac.show();
        System.out.println("finished");

        new gui();
    }
}
