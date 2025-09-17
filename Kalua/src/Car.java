public class Car {
    private int carId;
    private String registrationNo;

    private String brand;
    private String model;
    private String fuelType;
    private short firstRegYear;
    private byte firstRegMonth;
    private int odometerKm;

    private int carGroupId;

    public Car(int carId, String registrationNo, String brand, String model, String fuelType, short firstRegYear, byte firstRegMonth, int odometerKm, int carGroupId){
        this.carId = carId;

        this.registrationNo = registrationNo;
        this.brand = brand;
        this.model = model;
        this.fuelType = fuelType;
        this.firstRegYear = firstRegYear;
        this.firstRegMonth = firstRegMonth;
        this.odometerKm = odometerKm;

        this.carGroupId = carGroupId;
    }

    public int getCarId(){return carId;}
    public void setCarId(int carId){this.carId = carId;}

    public String getRegistrationNo(){ return registrationNo;}
    public void setRegistrationNo(String registrationNo){
        this.registrationNo = registrationNo;
    }
    public String getBrand(){return brand;}
    public void setBrand(String brand){this.brand = brand;}
    public String getModel(){return model;}
    public void setModel(String model){this.model = model;}
    public String getFuelType(){return fuelType;}
    public void setFuelType(String fuelType){this.fuelType = fuelType;}
    public short getFirstRegYear(){return firstRegYear;}
    public void setFirstRegYear(short firstRegYear){this.firstRegYear = firstRegYear;}
    public byte getFirstRegMonth(){return firstRegMonth;}
    public void setFirstRegMonth(byte firstRegMonth){this.firstRegMonth = firstRegMonth;}
    public int getOdometerKm(){return odometerKm;}
    public void setOdometerKm(int odometerKm){this.odometerKm = odometerKm;}
    public int getCarGroupId(){return carGroupId;}
    public void setCarGroupId(int carGroupId){this.carGroupId= carGroupId;}


    @Override
    public String toString(){
        return "Car | car id: " + carId + ", registration No:" + registrationNo + " " + brand + " " + model + ", " + fuelType + ", " + firstRegYear + "-" + firstRegMonth + ", odo: " + odometerKm + " car group: " + carGroupId;
    }
}
