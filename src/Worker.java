import java.util.ArrayList;
import java.util.Scanner;

public class Worker {
    private ArrayList<Branch> branches = new ArrayList<>();
    private String input;
    private final Scanner scanner = new Scanner(System.in);
    private String username;
    private String password;
    private String currentPassword;
    private String newPassword;
    private String newPasswordAgain;

    public void instructions(){
        boolean going = true;
        while (going) {
            System.out.println("#################################################################");
            System.out.println("\t\t\t\t\t\tCOMMERCIAL BANK");
            System.out.println("#################################################################");
            System.out.println("\n\t1. MANAGER LOGIN");
            System.out.println("\t2. CUSTOMER LOGIN");
            System.out.println("\t3. ADD NEW BRANCH");
            System.out.println("\t4. VIEW BRANCHES");
            System.out.println("\t5. EXIT");
            System.out.print("> ");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> login(false);
                case "2" -> login(true);
                case "3" -> addNewBranch();
                case "4" -> viewBranches();
                case "5" -> going = false;
                default -> System.out.println("\n\t\t[x] Invalid input!");
            }
        }
        System.out.println("\n\tTHANK YOU FOR BANKING WITH US!");
    }

    public String addNewCustomer(String username,String password,Branch branch){
        if(checkCustomer(username,password,false,"any",false) == null){
            branch.getCustomers().add(new Customer(username,password));
            return "\n\t[x] Successfully added new customer";
        }else {
            return "\n\t[x] Username is already in use";
        }
    }

    public Customer checkCustomer(String username,String password,boolean withPass,String branchName,boolean branchSpecified){
        for(Branch branch : branches){
            for(Customer customer : branch.getCustomers()){
                if(customer.getUsername().equals(username)){
                    if(branchSpecified){
                        if(branch.getBranchName().toUpperCase().equals(branchName.toUpperCase())){
                            return customer;
                        }
                    }
                    if(withPass){
                        if(customer.getPassword().equals(password)){
                            return customer;
                        }
                    }else{
                        return customer;
                    }
                }
            }
        }
        return null;
    }

    private void viewBranches() {
        System.out.println("#################################################################");
        System.out.println("\t\t\t\t\tCommercial Bank - Branches");
        System.out.println("#################################################################");
        for(Branch branch : branches){
            System.out.println("\t[x] " + branch.getBranchName().toUpperCase() + " [x]");
        }
    }

    private void addNewBranch(){
        System.out.println("#################################################################");
        System.out.println("\t\t\t\tCommercial Bank - Add new branch");
        System.out.println("#################################################################");
        String branchName;
        System.out.print("Enter branch name :- ");
        branchName = scanner.nextLine();
        boolean branchExist = false;
        for(Branch branch : branches){
            if (branch.getBranchName().toUpperCase().equals(branchName.toUpperCase())) {
                branchExist = true;
                break;
            }
        }if(branchExist){
            System.out.println("\n\t[x] Branch is already there");
        }else{
            System.out.print("Enter manager username :- ");
            username = scanner.nextLine();
            System.out.print("Enter manager password :- ");
            newPassword = scanner.nextLine();
            System.out.print("Enter manager password again :- ");
            newPasswordAgain = scanner.nextLine();
            if(!newPassword.equals(newPasswordAgain)){
                System.out.println("\n\t[x] Passwords aren't matched");
            }else{
                branches.add(new Branch(branchName,username,newPassword));
                System.out.println("\n\t[x] Successfully added new branch!");
            }
        }

    }

    private void login(boolean isCustomer){
        String type = (isCustomer)? "Customer":"Manager";
        System.out.println("#################################################################");
        System.out.println("\t\t\t\tCommercial Bank - "+ type+" Login");
        System.out.println("#################################################################");
        System.out.print("Enter username :- ");
        username = scanner.nextLine();
        System.out.print("Enter password :- ");;
        password = scanner.nextLine();
        boolean doesLogged = false;
        if(isCustomer){
            for(Branch branch : branches){
                if(checkCustomer(username,password,true,"any",false) != null){
                    doesLogged = true;
                    customerLogin(checkCustomer(username,password,false,"any",false), branch);
                }
            }
        }else{
            for(Branch branch : branches){
                if(branch.getManagerName().equals(username) && branch.getManagerPassword().equals(password)){
                    doesLogged = true;
                    managerLogin(branch);
                }
            }
        }
        if(!doesLogged){
            System.out.println("\n\t[x] Incorrect username or password");
        }
    }

    private void customerLogin(Customer customer, Branch branch) {
        boolean going = true;
        double amount;
        while (going){
            System.out.println("\n#################################################################");
            System.out.println("\tBranch - "+ branch.getBranchName().toUpperCase() +"\t\t\t\t\t\tUsername - "+ customer.getUsername().toUpperCase());
            System.out.println("#################################################################");
            System.out.println("\t0. CHECK BALANCE");
            System.out.println("\t1. ADD AMOUNT");
            System.out.println("\t2. GET AMOUNT");
            System.out.println("\t3. VIEW TRANSACTIONS");
            System.out.println("\t4. CHANGE PASSWORD");
            System.out.println("\t5. EXIT");
            System.out.print("> ");
            input = scanner.nextLine();
            switch (input) {
                case "0" -> System.out.println("Your current balance :- " + customer.getBalance() + "$");
                case "1" -> {
                    System.out.print("Enter amount :- ");
                    amount = scanner.nextDouble();
                    scanner.nextLine();
                    customer.addAmount(amount);
                    System.out.println("\n\t[x] Successfully added new amount");
                    System.out.println("Your current balance :- " + customer.getBalance());
                }
                case "2" -> {
                    System.out.print("Enter amount :- ");
                    amount = scanner.nextDouble();
                    scanner.nextLine();
                    if (amount > customer.getBalance()) {
                        System.out.println("\n\t[x] You haven't insufficient balance to get");
                    } else {
                        customer.addAmount(-amount);
                        System.out.println("Your current balance :- " + customer.getBalance() + " $");
                    }
                }
                case "3" -> {
                    for (Double transaction : customer.getTransactions()) {
                        System.out.print(transaction + " $\t");
                    }
                    System.out.println();
                }
                case "4" -> {
                    System.out.print("Enter current password :- ");
                    currentPassword = scanner.nextLine();
                    System.out.print("Enter new password :- ");
                    newPassword = scanner.nextLine();
                    System.out.print("Enter new password again :- ");
                    newPasswordAgain = scanner.nextLine();
                    if(currentPassword.equals(newPassword)){
                        System.out.println("\n\t[x] You have entered current password");
                    }
                    else if (!customer.getPassword().equals(currentPassword)) {
                        System.out.println("\n\t[x] Your current password is incorrect!");
                    } else if (!newPasswordAgain.equals(newPassword)) {
                        System.out.println("\n\t[x] New passwords aren't matched");
                    } else {
                        customer.setPassword(newPassword);
                        System.out.println("\n\t[x] Successfully changed password");
                    }
                }
                case "5" -> going = false;
                default -> System.out.println("\n\t[x] Invalid input!");
            }
        }
    }

    private void managerLogin(Branch branch) {
        boolean going = true;
        while (going){
            System.out.println("#################################################################");
            System.out.println("\t\t\t\t\t"+ branch.getBranchName().toUpperCase() +" Branch - Manager");
            System.out.println("#################################################################");

            System.out.println("\n1. ADD NEW CUSTOMER");
            System.out.println("2. BRANCH CUSTOMERS");
            System.out.println("3. BRANCH NET WORTH");
            System.out.println("4. CHANGE PASSWORD");
            System.out.println("5. DELETE CUSTOMER");
            System.out.println("6. EXIT");
            System.out.print("> ");
            input = scanner.nextLine();
            switch (input) {
                case "1" -> {
                    System.out.print("Enter username :- ");
                    username = scanner.nextLine();
                    System.out.print("Enter password :- ");
                    newPassword = scanner.nextLine();
                    System.out.print("Enter password again :- ");
                    newPasswordAgain = scanner.nextLine();
                    if (!newPassword.equals(newPasswordAgain)) {
                        System.out.println("\n\t[x] Passwords aren't matched");
                    } else {
                        System.out.println(addNewCustomer(username, newPassword,branch));
                    }
                }
                case "2" -> {
                    int customerCount = 0;
                    for (Customer customer : branch.getCustomers()) {
                        customerCount++;
                    }
                    System.out.println("Customers in your branch :- " + customerCount);
                }
                case "3" -> {
                    double branchNetWorth = 0;
                    for (Customer customer : branch.getCustomers()) {
                        for (Double transaction : customer.getTransactions()) {
                            branchNetWorth += transaction;
                        }
                    }
                    System.out.println("Your branch net worth :- " + branchNetWorth);
                }
                case "4" -> {
                    System.out.print("Enter current password :- ");
                    currentPassword = scanner.nextLine();
                    System.out.print("Enter new password :- ");
                    newPassword = scanner.nextLine();
                    System.out.print("Enter new password again :- ");
                    newPasswordAgain = scanner.nextLine();
                    if(currentPassword.equals(newPassword)){
                        System.out.println("\n\tYou have entered current password");
                    }
                    else if (!branch.getManagerPassword().equals(currentPassword)) {
                        System.out.println("\n\t[x] Current password is incorrect");
                    } else if (!newPassword.equals(newPasswordAgain)) {
                        System.out.println("\n\t[x] New passwords aren't matched");
                    } else {
                        branch.setManagerPassword(newPassword);
                        System.out.println("\n\t[x] Successfully changed your password!");
                    }
                }
                case "5" -> {
                    System.out.print("Enter customer username :- ");
                    username = scanner.nextLine();
                    System.out.print("Enter customer password :- ");
                    newPassword = scanner.nextLine();
                    System.out.print("Want to proceed ? :- ");
                    String promt = scanner.nextLine();
                    if (promt.equals("Yes") || promt.equals("yes") || promt.equals("y") || promt.equals("Y")) {
                        if (checkCustomer(username, newPassword, true,branch.getBranchName(),true) == null) {
                            System.out.println("\n\t[x] Can't find the customer");
                        } else {
                            branch.getCustomers().remove(checkCustomer(username, newPassword, true,branch.getBranchName(),true));
                            System.out.println("\n\t[x] Successfully deleted customer");
                        }
                    }
                }
                case "6" -> going = false;
                default -> System.out.println("\n\t[x] Invalid input!");
            }
        }
    }
}
