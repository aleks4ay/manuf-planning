package ua.aleks4ay.manufplan.domain.model;

import java.sql.Timestamp;

public class OrderPoint {
    private String id; //description.id
    private String idOrder; //description.iddoc  (by orders.iddoc)
    private String idTmc; //description.id_tmc
    private String tmcDescription; //techno_item.descr (by techno_item.id)
    private int position; //description.position
    private String description; //description.descr_second
    private int quantity = 0; //description.quantity
    private int quantityShipped = 0;
    private int tmcBalance = 0; //techno_item.store_c
    private Timestamp dateCreate; //orders.t_create
    private Timestamp dateToFactory; //orders.t_factory
    private Timestamp dateToShipment; //orders.t_end
    private String docNumber; //orders.docno
    private boolean closed = false;
    private String ClientName; //orders.client_name
    private String ManagerName; //orders.manager_name

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

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
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
