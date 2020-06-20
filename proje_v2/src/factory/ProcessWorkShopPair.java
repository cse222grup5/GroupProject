package factory;

public class ProcessWorkShopPair {
    private INamedItem process;
    private INamedItem workShop;
    public ProcessWorkShopPair(int idProcess,int idWorkshop){
        process=Factory.getInstance().getItem(Factory._PROCESS,idProcess);
        workShop=Factory.getInstance().getItem(Factory._WORKSHOP,idWorkshop);
    }
    public INamedItem getProcess(){
        return process;
    }
    public INamedItem getWorkshop(){
        return workShop;
    }
    public int getProcessId(){
    	return process.getId();
    }
    public String getProcessName(){
    	return process.getName();
    }
    public int getWorkShopId(){
    	return workShop.getId();
    }
    public String getWorkShopName(){
    	return workShop.getName();
    }
    public String toString(){
        return process.toString()+"\t"+ workShop.toString();
    }
}
