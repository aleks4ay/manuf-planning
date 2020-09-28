package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.new_model.*;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;
import ua.aleks4ay.manufplan.domain.view.ViewData;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class MainData {
    private static final String MIN_DEY_TO_FACTORY = "15-03-2020";
    private static final String MAX_DEY_TO_FACTORY = "31-12-2020";
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public static void main(String[] args) {
        MainData readerFromPostgres = new MainData();
        List<Description> allDescription = readerFromPostgres.getAllDescription();
        List<Description> openDescriptions = readerFromPostgres.filterOpenWithDate(
                allDescription, MIN_DEY_TO_FACTORY, MAX_DEY_TO_FACTORY );

        log.info("filterOpenDescription return {} Descriptions from Postgres.", openDescriptions.size());
//        openDescriptions.forEach(System.out::println);
        ViewData viewData = new ViewData();
        viewData.printHtml(openDescriptions);
    }

    public List<Description> getAllDescription() {
        List<Description> descriptions = new DescriptionReader().getAll();
        log.info("Was return {} Description from Postgres.", descriptions.size());
        Map<String, Tmc> technoProducts = new TmcReader().getAllAsMap();
        log.info("Was return {} technoProducts from Postgres.", technoProducts.size());
        Map<String, Order> orders = new OrderReader().getAllAsMap();
        log.info("Was return {} orders from Postgres.", orders.size());
        Map<String, List<Invoice>> invoiceMap = new InvoiceReader().getAllAsMapOfList();
        log.info("Was return {} List<Invoice> from Postgres.", invoiceMap.size());

        for (Description descr : descriptions) {
            String idTmc = descr.getTmc().getId();
            Tmc tmc = technoProducts.get(idTmc);
            if (tmc != null) {
                descr.setTmc(tmc);
            }
            String idOrder = descr.getIdOrder();
            Order order = orders.get(idOrder);
            if (order != null) {
                descr.setOrder(order);
            }

            List<Invoice> newInvoices = invoiceMap.get(idOrder);
            if (newInvoices != null) {
                for (Invoice invoice : newInvoices) {
                    if (idTmc.equals(invoice.getIdTmc())) {
                        descr.getInvoices().add(invoice);
                    }
                }
            }
        }
        return descriptions;
    }

    public List<Description> filterOpenWithDate (List<Description> descriptions, String minDayMonthYearDividedDash,
                                             String maxDayMonthYearDividedDash) {
        Timestamp dateStart = DateConverter.getTimestampFromString(minDayMonthYearDividedDash);
        Timestamp dateEnd = DateConverter.getTimestampFromString(maxDayMonthYearDividedDash);
        return descriptions.stream()
                .filter(d -> d.getQuantity() > d.getQuantityShipped())
                .filter(d -> d.getOrder().getDateToFactory().after(dateStart))
                .filter(d -> d.getOrder().getDateToFactory().before(dateEnd))
                .collect(Collectors.toList());
    }

}
