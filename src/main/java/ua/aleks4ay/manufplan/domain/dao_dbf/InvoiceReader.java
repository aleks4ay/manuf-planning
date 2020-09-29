package ua.aleks4ay.manufplan.domain.dao_dbf;

import com.linuxense.javadbf.*;
import ua.aleks4ay.manufplan.domain.dao_dbf.model_dbf.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class InvoiceReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, List<Invoice>> invoiceMap = new InvoiceReader().getAll(null);
        int i=0;
        for (List<Invoice> invoiceList : invoiceMap.values()) {
            System.out.print(++i);
            for (Invoice inv : invoiceList) {
                System.out.println(inv);
            }
            System.out.println("*****************");
        }
        System.out.println(invoiceMap.size());
    }

    public Map<String, List<Invoice>> getAll(Set<String> setId) { //Map<idOrder, List<Invoice>>
        Map<String, List<Invoice>> result = new HashMap<>();
        Map<String, Invoice> invoices = getHalfFillingInvoices(setId);
        invoices = fillEmptyField(invoices);
//        assert invoices != null;
        if (invoices != null) {
            for (Invoice invoice : invoices.values()) {
                String idOrder = invoice.getIdOrder();
                if (result.get(idOrder) == null) {
                    List<Invoice> newInvoiceList = new ArrayList<>();
                    newInvoiceList.add(invoice);
                    result.put(idOrder, newInvoiceList);
                } else /*if (!result.get(idOrder).contains(invoice))*/ {
                    List<Invoice> oldInvoiceList = result.get(idOrder);
//                System.out.println(oldInvoiceList.size());
                    oldInvoiceList.add(invoice);
                }
            }
        }
        return result;
    }

    private Map<String, Invoice> fillEmptyField(Map<String, Invoice> invoiceMap) {
        Map<String, String> orderMap = new HashMap<>();
        Map<String, Timestamp> timeToPayMap = new HashMap<>();
        Map<String, Invoice> result = new HashMap<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "DH3592.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String key = row.getString("SP3561");
                if (key.equals("   0     0")) {
                    continue;
                }

                String idInvoice = row.getString("IDDOC");
                String idOrder = row.getString("SP3561").substring(4);
                orderMap.put(idInvoice, idOrder);

                Date date = row.getDate("SP6358");
                Timestamp dateToPay;
                if (date == null) {
                    dateToPay = null;
                }
                else if (date.getTime() < 1560805200000L) {
                    continue;
                }
                else {
                    dateToPay = new Timestamp(date.getTime());
                }
                timeToPayMap.put(idInvoice, dateToPay);
            }

            for (Invoice fillableInvoice : invoiceMap.values()) {
                String idInvoice = fillableInvoice.getIdInvoice();
                Timestamp timeInvoice = timeToPayMap.get(idInvoice);
                if (timeInvoice == null) {
                    continue;
                }
                fillableInvoice.setIdOrder(orderMap.get(idInvoice));
                fillableInvoice.setTimeInvoice(timeInvoice);
                result.put(fillableInvoice.getId(), fillableInvoice);
            }

            return result;

        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'DH3592.dbf' from fillEmptyField(Map<String, Invoice>).", e);
        } catch (Exception e) {
            log.warn("Exception during writing 'Invoice'  from fillEmptyField(Map<String, Invoice>).", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Invoice not found.");
        return null;
    }

    private Map<String, Invoice> getHalfFillingInvoices(Set<String> setId) {
        if (setId == null) {
            throw new NullPointerException();
        }
        Map<String, Invoice> mapInvoice = new HashMap<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "DT3592.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String idInvoice = row.getString("IDDOC");
                if (! setId.contains(idInvoice)) {
                    continue;
                }
                int position = row.getInt("LINENO");
                String id = idInvoice + "-" + position;
                String idTmc = row.getString("SP3579");
                int quantity = row.getInt("SP3581");
                double newPayment = row.getDouble("SP3589");

                Invoice invoice = new Invoice(id, idInvoice, null, idTmc, null, null, quantity, newPayment);

                mapInvoice.put(id, invoice);
            }
            log.debug("Was read {} Invoice from 1C 'DH3592'.", mapInvoice.size());

            return mapInvoice;

        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'DH3592.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Invoice'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Invoice not found.");
        return null;
    }



}
