package _karlo_dragan.bankclientdb;


public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String oib;

    public Client(String firstName, String lastName, String address, String oib) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.oib = oib;
    }

    public Client(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    /*
    public void addAccount(Account account ){
        accountsList.add(account);
    }

    public List<Account> getAccountsList() {
        return accountsList;
    }
     */
    @Override
    public String toString() {
        return "Client{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", oib='" + oib + '\'' +
                '}';
    }
}

