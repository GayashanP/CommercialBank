import java.util.ArrayList;

public class Branch {
    private final String branchName;
    private final String managerName;
    private String managerPassword;
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    public Branch(String branchName, String managerName, String managerPassword) {
        this.branchName = branchName;
        this.managerName = managerName;
        this.managerPassword = managerPassword;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPassword() {
        return managerPassword;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setManagerPassword(String managerPassword) {
        this.managerPassword = managerPassword;
    }
}
