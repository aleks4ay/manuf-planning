package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.OrderDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.Order;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class OrderReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public List<Order> getOrdersBetweenDates(Timestamp dateStart, Timestamp dateEnd) {
        List<Order> orders = getAll();
        return filterOrdersBetweenDates(orders, dateStart, dateEnd);
    }

    public List<Order> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        OrderDao orderDao = new OrderDao(connPostgres);

        List<Order> orderList = orderDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Orders.", orderList.size());

        return orderList;
    }

    public Order getOneById(String idOrder) {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        OrderDao orderDao = new OrderDao(connPostgres);

        Order order = orderDao.getOneById(idOrder);

        utilDao.closeConnection(connPostgres);
        log.info("Was read Order with id {}.", idOrder);

        return order;
    }

    public Map<String, Order> getAllAsMap(List<Order> orders) {
        return orders
                .stream()
                .collect(Collectors.toMap(Order::getIdOrder, Order::getOrder));
    }

    public Map<String, Order> getAllAsMap() {
        List<Order> orders = getAll();
        return orders
                .stream()
                .collect(Collectors.toMap(Order::getIdOrder, Order::getOrder));
    }


    public List<Order> filterOrdersBetweenDates(List<Order> orders, Timestamp start, Timestamp end) {
        return orders.stream()
                .filter(o -> o.getDateToFactory().after(start))
                .filter(o -> o.getDateToFactory().before(end))
                .collect(Collectors.toList());
    }
}
