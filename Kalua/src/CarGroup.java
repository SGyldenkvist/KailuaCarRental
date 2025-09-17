public class CarGroup {
    private int carGroupId;

    private String name;

    public CarGroup(int carGroupId, String name){
        this.carGroupId = carGroupId;
        this.name = name;
    }

    public int getCarGroupId(){return carGroupId;}
    public void setCarGroupId(int carGroupId){this.carGroupId = carGroupId;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    @Override
    public String toString(){
        return "CarGroup id: " + carGroupId + ", " + name;
    }



}
