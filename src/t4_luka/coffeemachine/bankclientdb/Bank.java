package t4_luka.coffeemachine.bankclientdb;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private String address;

    private List<Client> clients =  new ArrayList<>();

    public List<Client> getClients() {
        return clients;
    }

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "bankName='" + name + '\'' +
                ", address='" + address + '\'' +
                ", clients=" + clients +
                '}' + "\n";
    }
}
