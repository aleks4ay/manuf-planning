package ua.aleks4ay.manufplan.domain.model;

import java.util.ArrayList;
import java.util.List;

public class BigTmc {

    private String id; //techno_item.id
    private String tmcDescription; //techno_item.descr (by techno_item.id)
    private int balance; //techno_item.store_c
    private String descrSecond; //description.descr_second
    private int sizeA; //description.size_a
    private int sizeB; //description.size_b
    private int sizeC; //description.size_c
    private int demand = 0; //techno_item.store_c
    private List<Description> descriptions;

    public BigTmc() {
    }

    public BigTmc(String id) {
        this.id = id;
    }

    public BigTmc(String id, String tmcDescription, int balance) {
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

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescrSecond() {
        return descrSecond;
    }

    public void setDescrSecond(String descrSecond) {
        this.descrSecond = descrSecond;
    }

    public int getSizeA() {
        return sizeA;
    }

    public void setSizeA(int sizeA) {
        this.sizeA = sizeA;
    }

    public int getSizeB() {
        return sizeB;
    }

    public void setSizeB(int sizeB) {
        this.sizeB = sizeB;
    }

    public int getSizeC() {
        return sizeC;
    }

    public void setSizeC(int sizeC) {
        this.sizeC = sizeC;
    }

    public BigTmc getBigTmc() {
        return this;
    }

    public void addDescription(Description newDescr) {
        boolean findEquals = false;
        if (descriptions == null) {
            List<Description> tempDescrList = new ArrayList<>();
            tempDescrList.add(newDescr);
            setDescriptions(tempDescrList);
            this.descrSecond = newDescr.getDescrSecond();
            this.sizeA = newDescr.getSizeA();
            this.sizeB = newDescr.getSizeB();
            this.sizeC = newDescr.getSizeC();
            return;
        }
        for (Description oldDescr : descriptions) {
            if (oldDescr.getId().equals(newDescr.getId())) {
                findEquals = true;
                break;
            }
        }
        if (! findEquals) {
            descriptions.add(newDescr);
        }
    }

    @Override
    public String toString() {
        return "BigTmc{" +
                "id='" + id + '\'' +
                ", descr='" + tmcDescription + '\'' +
                ", balance=" + balance +
                ", demand=" + demand +
                '}';
    }
}

