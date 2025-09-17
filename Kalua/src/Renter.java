import java.time.LocalDate;
import java.util.concurrent.BlockingDeque;

public class Renter {
    private int renterId;

    private String name;
    private String address;
    private String zip;
    private String city;
    private String mobilePhone;
    private String phone;
    private String email;
    private String driverLicenceNo;
    private LocalDate driverSinceDate;

    public Renter(int renterId, String name, String address, String zip, String city, String mobilePhone, String phone, String email, String driverLicenceNo, LocalDate driverSinceDate){
        this.renterId = renterId;
        this.name=name;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.mobilePhone = mobilePhone;
        this.phone = phone;
        this.email= email;
        this.driverLicenceNo = driverLicenceNo;
        this.driverSinceDate = driverSinceDate;
    }

    public int getRenterId(){return renterId;}
    public void setRenterId(int renterId){this.renterId = renterId;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}
    public String getZip(){return zip;}
    public void setZip(String zip){this.zip = zip;}
    public String getCity(){return city;}
    public void setCity(String city){this.city= city;}
    public String getMobilePhone(){return mobilePhone;}
    public void setMobilePhone(String mobilePhone){this.mobilePhone = mobilePhone;}
    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getDriverLicenceNo(){return driverLicenceNo;}
    public void setDriverLicenceNo(String driverLicenceNo){this.driverLicenceNo= driverLicenceNo;}
    public LocalDate getDriverSinceDate(){return driverSinceDate;}
    public void setDriverSinceDate(LocalDate driverSinceDate){this.driverSinceDate = driverSinceDate;}


    @Override
    public String toString(){
        return "Renter | renter id: " + renterId + ", " + name + ", " + address + ", " + zip + " " + city + ", mobile: " + mobilePhone + ", phone: " + phone + ", email: " + email + " licence no: " + driverLicenceNo + ", driver since: " + driverSinceDate ;
    }

}

