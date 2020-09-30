package ua.aleks4ay.manufplan.domain.model;

public class Tmc {

    private String id; //techno_item.id
    private String tmcDescription; //techno_item.descr (by techno_item.id)
    private int balance; //techno_item.store_c
    private int demand = 0; //techno_item.store_c

    public Tmc() {
    }

    public Tmc(String id) {
        this.id = id;
    }

    public Tmc(String id, String tmcDescription, int balance) {
        this.id = id;
        this.tmcDescription = tmcDescription;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTmcDescription() {
        return tmcDescription;
    }

    public void setTmcDescription(String tmcDescription) {
        this.tmcDescription = tmcDescription;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDemand() {
        return demand;
    }

    public void setDemand(int demand) {
        this.demand = demand;
    }

    public Tmc getTmc() {
        return this;
    }

    @Override
    public String toString() {
        return "Tmc{" +
                "id='" + id + '\'' +
                ", descr='" + tmcDescription + '\'' +
                ", balance=" + balance +
                '}';
    }
}

