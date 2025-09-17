import javax.security.auth.login.CredentialNotFoundException;
import java.time.LocalDateTime;

public class RentalContract {
    private int rentalContractId;

    private int carId;
    private int renterId;

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int maxKm;
    private int odometerStartKm;

    public RentalContract(int rentalContractId, int carId, int renterId, LocalDateTime fromDate, LocalDateTime toDate, int maxKm, int odometerStartKm){
        this.rentalContractId = rentalContractId;
        this.carId = carId;
        this.renterId = renterId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.maxKm= maxKm;
        this.odometerStartKm= odometerStartKm;
    }

    public int getRentalContractId(){return rentalContractId;}
    public void setRentalContractId(int rentalContractId){this.rentalContractId= rentalContractId;}
    public int getCarId(){return carId;}
    public void setCarId(int carId){this.carId = carId;}
    public int getRenterId(){return renterId;}
    public void setRenterId(int renterId){this.renterId = renterId;}
    public LocalDateTime getFromDate(){return fromDate;}
    public void setFromDate(LocalDateTime fromDate){this.fromDate = fromDate;}
    public LocalDateTime getToDate(){return toDate;}
    public void setToDate(LocalDateTime toDate){this.toDate = toDate;}
    public int getMaxKm(){return maxKm;}
    public void setMaxKm(int maxKm){this.maxKm = maxKm;}
    public int getOdometerStartKm(){return odometerStartKm;}
    public void setOdometerStartKm(int odometerStartKm){this.odometerStartKm= odometerStartKm;}


    @Override
    public String toString(){
        return "RentalContract | id: " + rentalContractId + ", Renter: " + renterId + ", car id: " + carId + ", from date: " + fromDate + ", to date: " + toDate + " maxKm: " + maxKm + ", Odometer start(km): " + odometerStartKm;
    }


}
