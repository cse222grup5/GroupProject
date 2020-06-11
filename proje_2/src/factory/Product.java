package factory;

import java.util.ArrayList;

public class Product extends NamedItem {
    private ArrayList<StockRawMaterial> rawMaterials;
    private ArrayList<ProcessWorkShopPair> processes;
    public Product(int id, String name, ArrayList<StockRawMaterial> rawMaterials, ArrayList<ProcessWorkShopPair> processes){
            super(id,name);
            this.rawMaterials=rawMaterials;
            this.processes=processes;
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append(super.toString()+"\n");
        sb.append("Raw Material\n");
        for(StockRawMaterial i:rawMaterials){
            sb.append(i.toString()+"\n");
        }
        sb.append("Process & Workshop\n");
        for(ProcessWorkShopPair i:processes){
            sb.append(i.toString()+"\n");
        }
        return sb.toString();
    }
    public ProcessWorkShopPair getProcessStep(int i){
        return processes.get(i);
    }
    public int getProcessCount(){
        return processes.size();
    }
    public StockRawMaterial getRawMaterial(int i){
        return rawMaterials.get(i);
    }
    public int getRawMaterialCount(){
        return rawMaterials.size();
    }
}
