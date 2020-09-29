package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao_dbf.*;
import ua.aleks4ay.manufplan.domain.dao_dbf.model_dbf.*;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;
import ua.aleks4ay.manufplan.domain.dao_dbf.DescriptionReader;
import ua.aleks4ay.manufplan.domain.dao_dbf.OrderReader;
import ua.aleks4ay.manufplan.domain.dao_dbf.TmcReader;
import ua.aleks4ay.manufplan.domain.dao_dbf.InvoiceReader;

import java.sql.Timestamp;
import java.util.*;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class ReaderFrom1S {
    private static final String MIN_DEY_TO_SHIPMENT = "15-03-2020";
    private static final String MAX_DEY_TO_SHIPMENT = "31-12-2020";
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    private Map<String, OrderPoint> orderPointMap = new HashMap<>();
    private Map<String, OrderPoint> openOrderPointMap = new HashMap<>();
    private Map<String, OrderPoint> closedOrderPointMap = new HashMap<>();

    public static void main(String[] args) {
        new ReaderFrom1S().printOpenOrdersFrom1S();
    }

    public void printOpenOrdersFrom1S() {
        Map<String, Tmc> technoProducts = getTechnoProduct();
        Map<String, Integer> tmcBalances = new TmcBalanceReader().getAll();
        Map<String, Journal> journalMap = new JournalReader().getAllJournal();
        Map<String, Order> orderMap = new OrderReader().getAll();
        Set<String> rangesIdOrder = getRangesIdOrder(journalMap, orderMap);
        Map<String, List<Invoice>> invoicesListMap = new InvoiceReader().getAll(journalMap.keySet());

        log.info("InvoiceReader return {} InvoicesList from 1S.", invoicesListMap.size());

        List<Description> descriptions = new DescriptionReader().getAll();
        log.info("DescriptionReader return {} Description from 1S.", descriptions.size());

        orderPointMap = buildOrderPointFromSomeDataCollection(journalMap, orderMap, technoProducts, descriptions,
                rangesIdOrder, tmcBalances);
        log.info("buildOrderPointFromSomeDataCollection return {} OrderPointMap from 1S.", orderPointMap.size());

        orderPointMap = fillFromInvoice(orderPointMap, invoicesListMap);
        log.info("fillFromInvoice return {} OrderPointMap from 1S.", orderPointMap.size());

        openOrderPointMap = filterOpenOrder(orderPointMap);
        log.info("filterOpenOrder return {} OrderPointMap from 1S.", openOrderPointMap.size());

        closedOrderPointMap = filterCloseOrder(orderPointMap);
        log.info("filterCloseOrder return {} OrderPointMap from 1S.", closedOrderPointMap.size());

        printOrder( openOrderPointMap.values(), MIN_DEY_TO_SHIPMENT, MAX_DEY_TO_SHIPMENT);
    }

    private Map<String, OrderPoint> filterOpenOrder(Map<String, OrderPoint> orderPointMap) {
        Map<String, OrderPoint> result = new HashMap<>();
        for (OrderPoint order : orderPointMap.values()) {
            if (order.getQuantity() != order.getQuantityShipped()) {
                result.put(order.getId(), order);
            }
        }
        return result;
    }

    private Map<String, OrderPoint> filterCloseOrder(Map<String, OrderPoint> orderPointMap) {
        Map<String, OrderPoint> result = new HashMap<>();
        for (OrderPoint order : orderPointMap.values()) {
            if (order.getQuantity() == order.getQuantityShipped()) {
                result.put(order.getId(), order);
            }
        }
        return result;
    }


    private Map<String, Tmc> getTechnoProduct() {
        TmcReader tmcReader = new TmcReader();
        List<Tmc> technoProducts = tmcReader.getAll();
        technoProducts = tmcReader.doTechnoFilter(technoProducts);
        Map<String, Tmc> result = new HashMap<>();
        for (Tmc tmc : technoProducts) {
            result.put(tmc.getId(), tmc);
        }
        log.debug("Was read {} TechnoProduct from 1S.", result.size());
        return result;
    }


    public Set<String> getRangesIdOrder(Map<String, Journal> journalMap, Map<String, Order> orderMap) {
        Set<String> rangesIdOrder = new HashSet<>();
        for (String key : journalMap.keySet()) {
            if (orderMap.containsKey(key)) {
                rangesIdOrder.add(key);
            }
        }
        return rangesIdOrder;
    }


    private Map<String, OrderPoint> buildOrderPointFromSomeDataCollection(Map<String, Journal> journalMap, Map<String,
                                    Order> orderMap, Map<String, Tmc> technoProducts, List<Description> descriptions,
                                    Set<String> rangesIdOrder, Map<String, Integer> tmcBalances) {
        Map<String, OrderPoint> result = new HashMap<>();

        for (Description desc : descriptions) {
            String idDoc = desc.getIdDoc();
            if (! rangesIdOrder.contains(idDoc)) {
                continue;
            }
            String idTmc = desc.getIdTmc();
            Tmc tmc = technoProducts.get(idTmc);
            if (tmc == null) {
                continue;
            }
            Journal journal = journalMap.get(idDoc);
            Order order = orderMap.get(idDoc);
            if (order == null) {
                continue;
            }
            String id = desc.getId();
            OrderPoint newOrderPoint = new OrderPoint(id);

            newOrderPoint.setIdOrder(idDoc);
            newOrderPoint.setIdTmc(idTmc);
            newOrderPoint.setTmcDescription(tmc.getDescr());
            newOrderPoint.setPosition(desc.getPosition());
            newOrderPoint.setDescription(desc.getDescrSecond());
            newOrderPoint.setQuantity(desc.getQuantity());
            newOrderPoint.setDocNumber(journal.getDocNumber());

            Timestamp dateCreate = journal.getDateCreate();
            Timestamp dateToFactory = order.getDateToFactory();
            int duration = order.getDurationTime();
            if ( dateToFactory == null) {
                dateToFactory = dateCreate;
            }
            Timestamp maximum = dateCreate.after(dateToFactory) ? dateCreate : dateToFactory;
            Timestamp dateEnd = new Timestamp(DateConverter.offset(maximum.getTime(), duration));

            newOrderPoint.setDateCreate(dateCreate);
            newOrderPoint.setDateToFactory(dateToFactory);
            newOrderPoint.setDateToShipment(dateEnd);
            Integer balance = tmcBalances.get(idTmc);
            if (balance != null) {
                newOrderPoint.setTmcBalance(balance);
            }

            result.put(id, newOrderPoint);
        }

        return result;
    }

    private Map<String, OrderPoint> fillFromInvoice(Map<String, OrderPoint> orderPointMap,
                                                    Map<String, List<Invoice>> invoicesListMap) {

        for (OrderPoint orderPoint : orderPointMap.values()){
            List<Invoice> invoices = invoicesListMap.get(orderPoint.getIdOrder());
            if (invoices != null) {
                for (Invoice invoice : invoices) {
                    if (orderPoint.getIdTmc().equals(invoice.getIdTmc())) {
                        int oldQuantityShipped = orderPoint.getQuantityShipped();
                        int newQuantityShipped = oldQuantityShipped + invoice.getQuantity();
                        orderPoint.setQuantityShipped(newQuantityShipped);
                        if (orderPoint.getQuantity() == newQuantityShipped) {
                            orderPoint.setClosed(true);
                        }
                    }
                }
            }
        }
        return orderPointMap;
    }

    public void printOrder(Iterable<OrderPoint> orderList, String minYearMonthDeyDividedDash, String maxYearMonthDeyDividedDash) {
        Timestamp dateStart = DateConverter.getTimestampFromString(minYearMonthDeyDividedDash);
        Timestamp dateEnd = DateConverter.getTimestampFromString(maxYearMonthDeyDividedDash);
        for (OrderPoint order : orderList) {
            if (order.getDateToShipment().after(dateStart) && order.getDateToShipment().before(dateEnd)) {
                System.out.println(order);
            }
        }
    }

}
