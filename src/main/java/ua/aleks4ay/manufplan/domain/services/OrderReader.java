package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.OrderDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.Order;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class OrderReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public List<Order> getOrdersBetweenDates(LocalDateTime localDateStart, LocalDateTime localDateEnd) {
        List<Order> orders = getAll();
        return filterOrdersBetweenDates(orders, localDateStart, localDateEnd);
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


    public List<Order> filterOrdersBetweenDates(List<Order> orders, LocalDateTime start, LocalDateTime end) {
        Timestamp dayStart = Timestamp.valueOf(start);
        Timestamp dayEnd = Timestamp.valueOf(end);
        return orders.stream()
                .filter(o -> o.getDateToFactory().after(dayStart))
                .filter(o -> o.getDateToFactory().before(dayEnd))
                .collect(Collectors.toList());
    }
}
