import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarService {

    private static final Scanner in = new Scanner(System.in);
    private static final List<Car> cars = new ArrayList<>();

    public static void listAllCars(){
        cars.clear();


        String sql = """
            SELECT car_id, registration_no, brand, model, fuel_type, first_reg_year, first_reg_month, odometer_km, car_group_id 
            FROM car
            """;

        try (Connection con = AccessDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)){

            while(rs.next()){
                Car car = new Car(
                        rs.getInt("car_id"),
                        rs.getString("registration_no"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("fuel_type"),
                        (short) rs.getInt("first_reg_year"),
                        (byte) rs.getInt("first_reg_month"),
                        rs.getInt("odometer_km"),
                        rs.getInt("car_group_id")
                );
                cars.add(car);
            }
        }catch (SQLException e){
            System.out.println("Error. Could not retrieve cars: ");
            e.printStackTrace();
        }

        if(cars.isEmpty()){
            System.out.println("No cars registered.");
        } else{
            for(Car car : cars){
                System.out.println(car);
            }
        }

    }

    public static void insertCar(){

        System.out.println("Register new car: ");
        System.out.println("Registration no: ");
        String regNo = in.nextLine();
        System.out.println("Brand:");
        String brand = in.nextLine();
        System.out.println("Model:");
        String model = in.nextLine();
        System.out.println("Fuel type:");
        String fuelType = in.nextLine();
        System.out.println("First reg year: ");
        short year = Short.parseShort(in.nextLine());
        System.out.println("First reg month:");
        byte month = Byte.parseByte(in.nextLine());
        System.out.println("Odometer km:");
        int odo = Integer.parseInt(in.nextLine());
        System.out.println("Car group id:");
        int groupId = Integer.parseInt(in.nextLine());

        Car car = new Car(0, regNo, brand, model, fuelType, year, month, odo, groupId);

        String sql = """
                INSERT INTO car(registration_no, brand, model, fuel_type, first_reg_year, first_reg_month, odometer_km, car_group_id)
                VALUES (?,?,?,?,?,?,?,?)
                """;

        try(Connection con = AccessDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, car.getRegistrationNo());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setString(4, car.getFuelType());
            ps.setShort(5, car.getFirstRegYear());
            ps.setByte(6, car.getFirstRegMonth());
            ps.setInt(7, car.getOdometerKm());
            ps.setInt(8, car.getCarGroupId());

            int rows = ps.executeUpdate();
            if (rows >0){
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next()){
                        int newId = keys.getInt(1);
                        car.setCarId(newId);
                    }
                }
                cars.add(car);
                System.out.println("The new car has been registered");
            }


        } catch (SQLException e){
            System.out.println("Error. Could not register the new car: ");
            e.printStackTrace();
        }

    }

    public static void deleteCar(){
        System.out.println("Delete car: ");
        System.out.println("Enter car id: ");
        int carId = Integer.parseInt(in.nextLine().trim());

        String sql = "DELETE FROM car WHERE car_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, carId);

            int rows =ps.executeUpdate();

            if (rows > 0){

                Car toDelete = null;
                for(Car c : cars){
                    if (c.getCarId() == carId){
                        toDelete = c;
                        break;
                    }
                }
                if (toDelete != null){
                    cars.remove(toDelete);
                }
                System.out.println("Car with car id: " + carId + " has been removed.");
            } else{
                System.out.println("No car matches the car id: " + carId);
            }
        } catch (SQLException e){
            System.out.println("Error. Could not delete car: ");
            e.printStackTrace();
        }
    }

    public static void updateOdometer(){
        System.out.println("Update odometer:");
        System.out.println("Enter car id:");
        int carId = Integer.parseInt(in.nextLine().trim());

        System.out.println("Enter new odometer(km): ");
        int newOdo = Integer.parseInt(in.nextLine().trim());

        String sql = "UPDATE car SET odometer_km = ? WHERE car_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, newOdo);
            ps.setInt(2, carId);

            int rows = ps.executeUpdate();

            if(rows > 0){
                for(Car c :cars){
                    if(c.getCarId() == carId){
                        c.setOdometerKm(newOdo);
                        break;
                    }
                }
                System.out.println("Odometer has been updated for car with car id: " + carId);
            }else{
                System.out.println("No car registered with car id: " + carId);
            }
        }catch (SQLException e){
            System.out.println("Error. Odometer update failed: ");
            e.printStackTrace();
        }
    }




}
