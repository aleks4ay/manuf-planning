package ua.aleks4ay.manufplan.domain.dao_dbf;

import java.io.*;
import com.linuxense.javadbf.*;
import ua.aleks4ay.manufplan.domain.dao_dbf.model_dbf.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class OrderReader  {
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, Order> orderMap = new OrderReader().getAll();
        for (Order c : orderMap.values()) {
            System.out.println(c.getIdDoc() + ", " + c.getIdClient() + ", " + c.getDateToFactory() + ", " +
                    c.getDurationTime() + ", " + c.getIdManager() + ", " + c.getPrice());
        }
        System.out.println(orderMap.size());
    }

    public Map<String, Order> getAll() {

        Map<String, Order> mapOrder = new HashMap<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "DH1898.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                int keyOrderToFactory = row.getInt("SP14694");
                if (keyOrderToFactory != 1) {
                    continue;
                }
                String idDoc = row.getString("IDDOC");
                String idClient = row.getString("SP1899");
                String idManager = row.getString("SP14680");
                int durationTime = row.getInt("SP14695");
                Date date = row.getDate("SP14836");
                Timestamp dateToFactory;
                if (date == null) {
                    dateToFactory = null;
                }
                else if (date.getTime() < 1560805200000L) {
                    continue;
                }
                else {
                    dateToFactory = new Timestamp(date.getTime());
                }
                double price = row.getDouble("SP14684");

                Order order = new Order(idDoc, idClient, idManager, durationTime, dateToFactory, price);

                mapOrder.put(idDoc, order);
            }
            log.debug("Was read {} orders from 1C 'DH1898'.", mapOrder.size());
            return mapOrder;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'DH1898.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Orders'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Orders not found.");
        return null;
    }
}
