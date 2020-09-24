package ua.aleks4ay.manufplan.domain.model;

import java.sql.Timestamp;

public class Invoice {
    private String id;
    private String idInvoice;
    private String idOrder;
    private String idTmc;
    private String docNomberTmc;
    private Timestamp timeInvoice;
    private int quantity = 0;
    private double payment;

    public Invoice(String id) {
        this.id = id;
    }

    public Invoice(String id, String idInvoice, String idOrder, String idTmc, String docNomberTmc,
                   Timestamp timeInvoice, int quantity, double payment) {
        this.id = id;
        this.idInvoice = idInvoice;
        this.idOrder = idOrder;
        this.idTmc = idTmc;
        this.docNomberTmc = docNomberTmc;
        this.timeInvoice = timeInvoice;
        this.quantity = quantity;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getDocNomberTmc() {
        return docNomberTmc;
    }

    public void setDocNomberTmc(String docNomberTmc) {
        this.docNomberTmc = docNomberTmc;
    }

    public Timestamp getTimeInvoice() {
        return timeInvoice;
    }

    public void setTimeInvoice(Timestamp timeInvoice) {
        this.timeInvoice = timeInvoice;
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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", idInvoice='" + idInvoice + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", idTmc='" + idTmc + '\'' +
                ", docNomberTmc='" + docNomberTmc + '\'' +
                ", timeInvoice=" + timeInvoice +
                ", quantity=" + quantity +
                ", payment=" + payment +
                '}';
    }
}
