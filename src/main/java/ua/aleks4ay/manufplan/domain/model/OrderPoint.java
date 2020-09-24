package ua.aleks4ay.manufplan.domain.model;

import java.sql.Timestamp;

public class OrderPoint {
    private String id;
    private String idOrder;
    private String idTmc;
    private String tmcDescription;
    private int position;
    private String description;
    private int quantity = 0;
    private int quantityShipped = 0;
    private int tmcBalance = 0;
    private Timestamp dateCreate;
    private Timestamp dateToFactory;
    private Timestamp dateToShipment;
    private String docNumber;
    private boolean closed = false;

    public OrderPoint(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
    }

    public String getTmcDescription() {
        return tmcDescription;
    }

    public void setTmcDescription(String tmcDescription) {
        this.tmcDescription = tmcDescription;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(int quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTmcBalance() {
        return tmcBalance;
    }

    public void setTmcBalance(int tmcBalance) {
        this.tmcBalance = tmcBalance;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(Timestamp dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public Timestamp getDateToShipment() {
        return dateToShipment;
    }

    public void setDateToShipment(Timestamp dateToShipment) {
        this.dateToShipment = dateToShipment;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Override
    public String toString() {
        return  idTmc + '\t' + tmcDescription + '\t' + id + '\t' + idOrder + '\t' +
                docNumber +
                '\t' + quantity + '\t' + quantityShipped + '\t' + tmcBalance + '\t' +
                dateCreate + '\t' + dateToShipment + '\t' + closed + '\t' + description;
    }
}
