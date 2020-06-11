package factory;

public class ProcessWorkShopPair {
    private INamedItem process;
    private INamedItem workshop;
    public ProcessWorkShopPair(int idProcess,int idWorkshop){
        process=Factory.getInstance().getItem(Factory._PROCESS,idProcess);
        workshop=Factory.getInstance().getItem(Factory._WORKSHOP,idWorkshop);
    }
    public INamedItem getProcess(){
        return process;
    }
    public INamedItem getWorkshop(){
        return workshop;
    }
    public String toString(){
        return process.toString()+"\t"+ workshop.toString();
    }
}
