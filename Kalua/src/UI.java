import java.util.Scanner;

public class UI {

    private static final Scanner in = new Scanner(System.in);

    public static void start(){

        boolean running = true;

        while(running){
            System.out.println("Kailua Car Rental");
            System.out.println("1. View car/renter/contract");
            System.out.println("2. Create car/renter/contract");
            System.out.println("3. Delete car/renter/contract");
            System.out.println("4. Update car/renter/contract");
            System.out.println("0. Exit");

            int choice = readInt("Choose action: ");

            switch (choice){
                case 1:
                    handleEntity("view");
                    break;
                case 2:
                    handleEntity("create");
                    break;
                case 3:
                    handleEntity("delete");
                    break;
                case 4:
                    handleEntity("update");
                    break;
                case 0:
                    System.out.println("Bye");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
        }
    }


    private static void handleEntity(String action){
        System.out.println("What do you want to " + action + "?");
        System.out.println("1. Car");
        System.out.println("2. Renter");
        System.out.println("3. RentalContract");
        System.out.println("0. Back");

        int entity = readInt("Choose: ");

        switch(entity){
            case 1:
                if("view".equals(action)) CarService.listAllCars();
                else if("create".equals(action)) CarService.insertCar();
                else if("delete".equals(action)) CarService.deleteCar();
                else if("update".equals(action)) CarService.updateOdometer();
                break;
            case 2:
                if("view".equals(action)) RenterService.listAllRenters();
                else if("create".equals(action)) RenterService.insertRenter();
                else if("delete".equals(action)) RenterService.deleteRenter();
                else if("update".equals(action)) RenterService.updatePhone();
                break;
            case 3:
                if("view".equals(action)) RentalContractService.listAllRenterContracts();
                else if("create".equals(action)) RentalContractService.insertRenterContract();
                else if("delete".equals(action)) RentalContractService.deleteRenterContract();
                else if("update".equals(action)) RentalContractService.updateRenterContractToDate();
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid choice");

        }
    }

    private static int readInt(String msg){
        System.out.println(msg);

        while(!in.hasNextInt()){
            System.out.println("Invalid input, try again.");
            in.nextLine();
            System.out.println(msg);
        }

        int val = in.nextInt();
        in.nextLine();
        return val;

    }

}
