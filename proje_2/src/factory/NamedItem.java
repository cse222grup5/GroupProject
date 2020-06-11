package factory;

public class NamedItem implements INamedItem {
    private int id;
    private String name;

    public NamedItem(int id, String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public String toString(){
        return id+"\t"+name;
    }
}
