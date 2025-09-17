import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RenterService {

    private static final Scanner in = new Scanner(System.in);
    private static final List<Renter> renters = new ArrayList<>();

    public static void listAllRenters(){
        renters.clear();

        String sql = """
                SELECT renter_id, name, address, zip, city, mobile_phone, phone, email, driver_licence_no, driver_since_date 
                FROM renter
                ORDER BY renter_id
                """;

        try(Connection con = AccessDB.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql)){

            System.out.println("Renters:");

            while(rs.next()){

                java.sql.Date d= rs.getDate("driver_since_date");
                LocalDate since = (d == null) ? null : d.toLocalDate();

                Renter renter = new Renter(
                        rs.getInt("renter_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("zip"),
                        rs.getString("city"),
                        rs.getString("mobile_phone"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("driver_licence_no"),
                        since
                );
                renters.add(renter);

            }

        } catch (SQLException e){
            System.out.println("Eroor. Could not retrieve renters: ");
            e.printStackTrace();
        }

        if(renters.isEmpty()){
            System.out.println("No renters registered.");
        } else {
            for (Renter r : renters){
                System.out.println(r);
            }
        }
    }


    public static void insertRenter(){
        System.out.println("Register new renter:");

        System.out.println("Name: ");
        String name = in.nextLine();
        System.out.println("Address:");
        String address = in.nextLine();
        System.out.println("Zip:");
        String zip = in.nextLine();
        System.out.println("City:");
        String city = in.nextLine();
        System.out.println("Mobile phone:");
        String mobilePhone = in.nextLine();
        System.out.println("Phone:");
        String phone = in.nextLine();
        System.out.println("Email:");
        String email = in.nextLine();
        System.out.println("Driver licence number:");
        String driverLicenceNo = in.nextLine();

        LocalDate driverSinceDate = readLocalDate("Driver since date (yyyy-mm-dd): ");


        Renter renter = new Renter(0, name, address, zip, city, mobilePhone, phone, email, driverLicenceNo, driverSinceDate);

        String sql = """
                INSERT INTO renter
                (name, address, zip, city, mobile_phone, phone, email, driver_licence_no, driver_since_date)
                VALUES (?,?,?,?,?,?,?,?,?)
                """;

        try(Connection con = AccessDB.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, renter.getName());
            ps.setString(2, renter.getAddress());
            ps.setString(3, renter.getZip());
            ps.setString(4, renter.getCity());
            ps.setString(5, renter.getMobilePhone());
            ps.setString(6, renter.getPhone());
            ps.setString(7, renter.getEmail());
            ps.setString(8, renter.getDriverLicenceNo());
            ps.setDate(9, java.sql.Date.valueOf(renter.getDriverSinceDate()));

            int rows = ps.executeUpdate();

            if(rows > 0){
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next()){
                        int newId = keys.getInt(1);
                        renter.setRenterId(newId);
                    }
                }
                renters.add(renter);
                System.out.println("The renter has been registered");
            }else{
                System.out.println("the registration failed");
            }
        } catch (SQLIntegrityConstraintViolationException dup){
            System.out.println("Registration failed because of constraints (UNIQUE/Not NULL/FK): " + dup.getMessage());
        } catch (SQLException e){
            System.out.println("Failed. Could not register the new renter");
            e.printStackTrace();
        }

    }
    private static LocalDate readLocalDate(String prompt){
        while(true){
            System.out.print(prompt);
            String s = in.nextLine().trim();

            try {
                return LocalDate.parse(s);
            } catch(DateTimeParseException e){
                System.out.println("Invalid date format. Use yyyy-mm-dd");

            }
        }
    }

    public static void deleteRenter() {
        System.out.println("Delete renter: ");

        System.out.println("Enter renter id: ");
        int renterId = Integer.parseInt(in.nextLine().trim());

        String sql = "DELETE FROM renter WHERE renter_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, renterId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                Renter toDelete = null;
                for (Renter r : renters) {
                    if (r.getRenterId() == renterId) {
                        toDelete = r;
                        break;
                    }
                }
                if (toDelete != null) {
                    renters.remove(toDelete);
                }
                System.out.println("Renter with renter id: " + renterId + " has been removed.");
            } else {
                System.out.println("No renter matches the renter id: " + renterId);
            }

        } catch (SQLIntegrityConstraintViolationException fk) {
            System.out.println("Cannot delete renter: related rental contracts exist. Close/delete those first. Message: " + fk.getMessage());
        } catch (SQLException e) {
            System.out.println("Error. Could not delete renter: ");
            e.printStackTrace();
        }
    }

    public static void updatePhone() {
        System.out.println("Update renter phone:");
        System.out.println("Enter renter id:");
        int renterId = Integer.parseInt(in.nextLine().trim());

        System.out.println("Enter new phone:");
        String newPhone = in.nextLine().trim();

        String sql = "UPDATE renter SET phone = ? WHERE renter_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPhone);
            ps.setInt(2, renterId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                for (Renter r : renters) {
                    if (r.getRenterId() == renterId) {
                        r.setPhone(newPhone);
                        break;
                    }
                }
                System.out.println("Phone updated for renter id: " + renterId);
            } else {
                System.out.println("No renter found with id: " + renterId);
            }

        } catch (SQLIntegrityConstraintViolationException dup) {
            System.out.println("Update failed due to constraint (e.g. UNIQUE): " + dup.getMessage());
        } catch (SQLException e) {
            System.out.println("Error. Phone update failed: ");
            e.printStackTrace();
        }
    }




}
