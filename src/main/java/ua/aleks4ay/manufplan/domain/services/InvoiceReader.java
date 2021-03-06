package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.InvoiceDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.Invoice;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class InvoiceReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());


    public List<Invoice> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        InvoiceDao invoiceDao = new InvoiceDao(connPostgres);

        List<Invoice> invoiceList = invoiceDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Invoices.", invoiceList.size());

        return invoiceList;
    }

    public Map<String, Invoice> getAllAsMap() {
        List<Invoice> result = getAll();
        return result
                .stream()
                .collect(Collectors.toMap(Invoice::getId, Invoice::getInvoice));
    }

    public Map<String, List<Invoice>> getAllAsMapOfList() {
        Map<String, List<Invoice>> result = new HashMap<>();
        List<Invoice> invoices = getAll();
        for (Invoice invoice : invoices) {
            String idOrder = invoice.getIdOrder();
            if (result.get(idOrder) == null) {
                List<Invoice> newInvoiceList = new ArrayList<>();
                newInvoiceList.add(invoice);
                result.put(idOrder, newInvoiceList);
            } else {
                List<Invoice> oldInvoiceList = result.get(idOrder);
                oldInvoiceList.add(invoice);
            }
        }
        return result;
    }
}
