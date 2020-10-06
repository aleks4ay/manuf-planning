package ua.aleks4ay.manufplan.domain.model;

import ua.aleks4ay.manufplan.domain.services.OrderReader;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;

import java.sql.Timestamp;

public class ManufactureTechno {
    private String id;
    private String idManuf;
    private String docNumber;
    private String idTmc;
    private String idOrder;
    private int position;
    private String tmcDescr = "";
    private Timestamp timeManufacture;
    private int quantity;
    private String descrSecond = "";
    private int sizeA = 0;
    private int sizeB = 0;
    private int sizeC = 0;
    private String embodiment = "";

    public ManufactureTechno() {
    }

    public ManufactureTechno(String id, String idManuf, String docNumber, String idTmc, String idOrder, int position,
                             Timestamp timeManufacture, int quantity) {
        this.id = id;
        this.idManuf = idManuf;
        this.docNumber = docNumber;
        this.idTmc = idTmc;
        this.idOrder = idOrder;
        this.position = position;
        this.timeManufacture = timeManufacture;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdManuf() {
        return idManuf;
    }

    public void setIdManuf(String idManuf) {
        this.idManuf = idManuf;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public Timestamp getTimeManufacture() {
        return timeManufacture;
    }

    public void setTimeManufacture(Timestamp timeManufacture) {
        this.timeManufacture = timeManufacture;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
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

    public String getEmbodiment() {
        return embodiment;
    }

    public void setEmbodiment(String embodiment) {
        this.embodiment = embodiment;
    }

    public ManufactureTechno getManufacture() {
        return this;
    }

    public String getTimeManufactureString() {
        return DateConverter.dateToString(timeManufacture.getTime());
    }

    public String getTmcDescr() {
        return tmcDescr;
    }

    public void setTmcDescr(String tmcDescr) {
        this.tmcDescr = tmcDescr;
    }

    public String getDocNumberOrder() {
        Order order = new OrderReader().getOneById(idOrder);
        if (order != null) {
            return order.getDocNumber();
        }
        return "-";
    }

    @Override
    public String toString() {
        return "ManufactureTechno{" +
                "id='" + id + '\'' +
                ", idManuf='" + idManuf + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", idTmc='" + idTmc + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", position=" + position +
                ", timeManufacture=" + timeManufacture +
                ", quantity=" + quantity +
                ", descrSecond='" + descrSecond + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", embodiment='" + embodiment + '\'' +
                '}';
    }
}
