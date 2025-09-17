import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RentalContractService {

    private static final Scanner in = new Scanner(System.in);
    private static final List<RentalContract> contracts = new ArrayList<>();
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public static void listAllRenterContracts() {
        contracts.clear();

        String sql = """
            SELECT rental_contract_id, car_id, renter_id, from_date, to_date, max_km, odometer_start_km
            FROM rental_contract
            ORDER BY rental_contract_id
            """;

        try (Connection con = AccessDB.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("Rental contracts:");
            while (rs.next()) {
                Timestamp fts = rs.getTimestamp("from_date");
                Timestamp tts = rs.getTimestamp("to_date");

                LocalDateTime from = (fts == null) ? null : fts.toLocalDateTime();
                LocalDateTime to   = (tts == null) ? null : tts.toLocalDateTime();

                RentalContract c = new RentalContract(
                        rs.getInt("rental_contract_id"),
                        rs.getInt("car_id"),
                        rs.getInt("renter_id"),
                        from,
                        to,
                        rs.getInt("max_km"),
                        rs.getInt("odometer_start_km")
                );
                contracts.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error. Could not retrieve rental contracts:");
            e.printStackTrace();
        }

        if (contracts.isEmpty()) {
            System.out.println("No contracts registered.");
        } else {
            for (RentalContract c : contracts) {
                System.out.println(c);
            }
        }
    }

    public static void insertRenterContract() {
        System.out.println("Create rental contract:");

        int renterId = readInt("Renter id: ");
        int carId= readInt("Car id: ");

        LocalDateTime fromDate = readLocalDateTime("From date/time (yyyy-MM-dd HH:mm): ");
        LocalDateTime toDate = readOptionalLocalDateTime("To date/time (yyyy-MM-dd HH:mm, optional – blank for none): ");

        int maxKm = readInt("Max km: ");
        int odometerStartKm = readInt("Odometer start (km): ");

        RentalContract contract = new RentalContract(
                0, carId, renterId, fromDate, toDate, maxKm, odometerStartKm
        );

        String sql = """
            INSERT INTO rental_contract
              (car_id, renter_id, from_date, to_date, max_km, odometer_start_km)
            VALUES (?,?,?,?,?,?)
            """;

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, contract.getCarId());
            ps.setInt(2, contract.getRenterId());
            ps.setTimestamp(3, Timestamp.valueOf(contract.getFromDate()));
            if (contract.getToDate() == null) ps.setNull(4, Types.TIMESTAMP);
            else ps.setTimestamp(4, Timestamp.valueOf(contract.getToDate()));
            ps.setInt(5, contract.getMaxKm());
            ps.setInt(6, contract.getOdometerStartKm());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int newId = keys.getInt(1);
                        contract.setRentalContractId(newId);
                    }
                }
                contracts.add(contract);
                System.out.println("Rental contract has been created.");
            } else {
                System.out.println("Insert failed.");
            }

        } catch (SQLIntegrityConstraintViolationException fk) {
            System.out.println("Insert failed due to constraint (FK/UNIQUE/NOT NULL): " + fk.getMessage());
        } catch (SQLException e) {
            System.out.println("Eroor. Could not create rental contract:");
            e.printStackTrace();
        }
    }

    public static void deleteRenterContract() {
        System.out.println("Delete rental contract:");
        int contractId = readInt("Enter contract id: ");

        String sql = "DELETE FROM rental_contract WHERE rental_contract_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, contractId);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                RentalContract toDelete = null;
                for (RentalContract c : contracts) {
                    if (c.getRentalContractId() == contractId) {
                        toDelete = c;
                        break;
                    }
                }
                if (toDelete != null) contracts.remove(toDelete);

                System.out.println("Contract with id " + contractId + " has been deleted.");
            } else {
                System.out.println("No contract matches id: " + contractId);
            }

        } catch (SQLException e) {
            System.out.println("Error. Could not delete contract:");
            e.printStackTrace();
        }
    }

    public static void updateRenterContractToDate() {
        System.out.println("Update contract 'to' date/time:");
        int contractId = readInt("Enter contract id: ");

        LocalDateTime newTo = readLocalDateTime("New to date/time (yyyy-MM-dd HH:mm): ");

        String sql = "UPDATE rental_contract SET to_date = ? WHERE rental_contract_id = ?";

        try (Connection con = AccessDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(newTo));
            ps.setInt(2, contractId);

            int rows = ps.executeUpdate();
            if (rows > 0) {

                for (RentalContract c : contracts) {
                    if (c.getRentalContractId() == contractId) {
                        c.setToDate(newTo);
                        break;
                    }
                }
                System.out.println("to_date updated for contract id: " + contractId);
            } else {
                System.out.println("No contract found with id: " + contractId);
            }

        } catch (SQLException e) {
            System.out.println("Error. Could not update contract:");
            e.printStackTrace();
        }
    }


    private static int readInt(String msg) {
        System.out.print(msg);
        while (!in.hasNextInt()) {
            System.out.println("Invalid input, try again.");
            in.nextLine();
            System.out.print(msg);
        }
        int val = in.nextInt();
        in.nextLine();
        return val;
    }

    private static LocalDateTime readLocalDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            try {
                return LocalDateTime.parse(s, DT_FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use yyyy-MM-dd HH:mm.");
            }
        }
    }

    private static LocalDateTime readOptionalLocalDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = in.nextLine().trim();
            if (s.isEmpty()) return null;
            try {
                return LocalDateTime.parse(s, DT_FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use yyyy-MM-dd HH:mm or leave blank.");
            }
        }
    }
}





