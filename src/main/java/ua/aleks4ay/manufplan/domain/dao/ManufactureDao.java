package ua.aleks4ay.manufplan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.model.ManufactureTechno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class ManufactureDao {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ALL = "SELECT * FROM manufacture_techno;";
    private static final String SQL_GET_ONE = "SELECT * FROM manufacture_techno WHERE id_tmc = ?;";

    public ManufactureDao(Connection conn) {
        this.connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }


    public List<ManufactureTechno> getAll() {
        List<ManufactureTechno> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'ManufactureTechno'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String id = rs.getString("id");
                String idManuf = rs.getString("id_manuf");
                String docNomberInvoice = rs.getString("docno");
                String idTmc = rs.getString("id_tmc");
                String idOrder = rs.getString("id_order");
                int position = rs.getInt("position");
                Timestamp timeManufacture = rs.getTimestamp("time_manuf");
                int quantity = rs.getInt("quantity");

                ManufactureTechno manufacture = new ManufactureTechno(id, idManuf, docNomberInvoice, idTmc, idOrder,
                        position, timeManufacture, quantity);

                result.add(manufacture);
            }
            log.debug("Was read {} ManufactureTechno from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'ManufactureTechno'.", e);
        }
        log.debug("Manufactures not found.");
        return null;
    }

    public List<ManufactureTechno> getByIdTmc(String idTmc) {
        List<ManufactureTechno> result = new ArrayList<>();
        try {
            PreparedStatement ps = connPostgres.prepareStatement(SQL_GET_ONE);
            ps.setString(1, idTmc);
            ResultSet rs = ps.executeQuery();
            log.debug("Select all 'ManufactureTechno'. SQL = {}.", SQL_GET_ONE);

            while (rs.next()) {
                String id = rs.getString("id");
                String idManuf = rs.getString("id_manuf");
                String docNomberInvoice = rs.getString("docno");
                String idOrder = rs.getString("id_order");
                int position = rs.getInt("position");
                Timestamp timeManufacture = rs.getTimestamp("time_manuf");
                int quantity = rs.getInt("quantity");

                ManufactureTechno manufacture = new ManufactureTechno(id, idManuf, docNomberInvoice, idTmc, idOrder,
                        position, timeManufacture, quantity);

                result.add(manufacture);
            }
            log.debug("Was read {} ManufactureTechno from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'ManufactureTechno'.", e);
        }
        log.debug("Manufactures not found.");
        return null;
    }
}
