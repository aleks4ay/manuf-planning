package ua.aleks4ay.manufplan.domain.model;

import ua.aleks4ay.manufplan.domain.tools.DateConverter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class Description{

    private String id; //description.id
    private String idOrder; //description.iddoc
    private int position; //description.position
    private String idTmc; //description.id_tmc
    private int quantity; //description.quantity
    private String descrSecond; //description.descr_second
    private int sizeA; //description.size_a
    private int sizeB; //description.size_b
    private int sizeC; //description.size_c

    private Order order;
    private Manufacture manuf;
    private Set<Invoice> invoices = new HashSet<>();

    public Description() {
    }

    public Description(String id, String idOrder, String idTmc, int position, int quantity, String descrSecond,
                       int sizeA, int sizeB, int sizeC) {
        this.id = id;
        this.idOrder = idOrder;
        this.idTmc = idTmc;
        this.position = position;
        this.quantity = quantity;
        this.descrSecond = descrSecond;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Manufacture getManuf() {
        return manuf;
    }

    public void setManuf(Manufacture manuf) {
        this.manuf = manuf;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }


    public int getQuantityShipped() {
        int result = 0;
        if (invoices != null) {
            for (Invoice invoice : invoices) {
                result += invoice.getQuantity();
            }
        }
        return result;
    }

    public int getNeedToShipment() {
        int result = getQuantity() - getQuantityShipped();
        return result > 0 ? result : 0;
    }

    public String getDocNumber() {
        return order.getDocNumber();
    }

    public void setDocNumber(String docNumber) {
        this.order.setDocNumber(docNumber);
    }

    public String getDocInvoice() {
        String result = "";
        for (Invoice invoice : invoices) {
            String dateInvoice = DateConverter.dateToString(order.getDateCreate().getTime());
            result += invoice.getDocNomberInvoice() + " (" + dateInvoice + ") ";
        }
        return result;
    }

    public void setDocInvoice(String docInvoiceListString) {
        //// TODO: 30.09.2020
    }

    public String getManager() {
        return order.getManagerName();
    }

    public void setManager(String manager) {
        this.order.setManagerName(manager);
    }

    public String getClient() {
        return order.getClientName();
    }

    public void setClient(String client) {
        this.order.setClientName(client);
    }

    public String getDateCreate() {
        return DateConverter.dateToString(order.getDateCreate().getTime());
    }

    public void setDateCreate(String dateCreate) {
        this.order.setDateCreate(Timestamp.valueOf(dateCreate));
    }

    public String getDateToFactory() {
        return DateConverter.dateToString(order.getDateToFactory().getTime());
    }

    public void setDateToFactory(String dateToFactory) {
        this.order.setDateToFactory(Timestamp.valueOf(dateToFactory));
    }

    public void addInvoice(Invoice newInvoice) {
        invoices.add(newInvoice);
    }

    @Override
    public String toString() {
        return "Description{" +
                "id='" + id + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", position=" + position +
                ", idTmc='" + idTmc + '\'' +
                ", quantity=" + quantity +
                ", descrSecond='" + descrSecond + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", order=" + order +
                ", manuf=" + manuf +
                ", invoices=" + invoices +
                '}';
    }
}
