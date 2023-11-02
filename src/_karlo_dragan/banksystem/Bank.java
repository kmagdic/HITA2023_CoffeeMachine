package _karlo_dragan.banksystem;

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

    public void addNewClient(String name, String surname, String address, String oib){
        Client client = new Client(name,surname, address,oib);
        clients.add(client);
    }

    public List<Client> findClientByName(String name){
        List<Client> findedClientsByName = new ArrayList<>();
        for (Client c: clients) {
            if (c.getFirstName().equals(name)){
                findedClientsByName.add(c);
            }
        }
        return findedClientsByName;
    }

    public Client findClientByOib(String oib){
        for (Client c: clients) {
            if (c.getOib().equals(oib)){
                return c;
            }
        }
        return null;
    }

    public Client deleteClient(String oib){
        for (Client c: clients) {
            if (c.getOib().equals(oib)){
                clients.remove(c);
                return c;
            }
        }
        return null;
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
