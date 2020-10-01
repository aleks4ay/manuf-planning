package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.DescriptionDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.Description;
import ua.aleks4ay.manufplan.domain.model.Invoice;
import ua.aleks4ay.manufplan.domain.model.Order;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class DescriptionReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());


    public List<Description> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        DescriptionDao descriptionDao = new DescriptionDao(connPostgres);

        List<Description> descriptionList = descriptionDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Descriptions.", descriptionList.size());

        return descriptionList;
    }

    public List<Description> getOpenDescription() {
        return getAll().stream()
                .filter(d -> d.getQuantity() > d.getQuantityShipped())
                .collect(Collectors.toList());
    }

    public List<Description> setOrderAllAndDeleteUnnecessary(List<Description> descriptions, Map<String, Order> orderMap) {
        List<Description> result = new ArrayList<>();
        for (Description descr : descriptions) {
            Order order = orderMap.get(descr.getIdOrder());
            if (order != null) {
                descr.setOrder(order);
                result.add(descr);
            }
        }
        return result;
    }

    public List<Description> setInvoiceAll(List<Description> descriptions, Map<String, List<Invoice>> invoiceMapOfList) {
        for (Description descr : descriptions) {
            List<Invoice> newInvoices = invoiceMapOfList.get(descr.getIdOrder());
            if (newInvoices != null) {
                newInvoices.stream()
                        .filter(invoice -> descr.getIdTmc().equals(invoice.getIdTmc()))
                        .forEach(invoice -> descr.getInvoices().add(invoice));
            }
        }
        return descriptions;
    }


    public List<Description> sortByDateFactory(List<Description> oldDescriptions) {
        Comparator<Description> comparatorIdTmc = (o1, o2) ->
                o1.getOrder().getDateToFactory().compareTo(o2.getOrder().getDateToFactory());
        Collections.sort(oldDescriptions, comparatorIdTmc);
        return oldDescriptions;
    }

    public List<Description> removeClosedPosition(List<Description> descriptions) {
        List<Description> result = new ArrayList<>();
        for (Description descr : descriptions) {
            if (descr.getNeedToShipment() > 0) {
                result.add(descr);
            }
        }
        return result;
    }
}
