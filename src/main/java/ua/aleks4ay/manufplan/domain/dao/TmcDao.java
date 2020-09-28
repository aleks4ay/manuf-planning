package ua.aleks4ay.manufplan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.new_model.Tmc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class TmcDao {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ALL = "SELECT * FROM techno_item;";

    public TmcDao(Connection conn) {
        this.connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }

    public List<Tmc> getAll() {
        List<Tmc> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Tmc'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String idTmc = rs.getString("id");
                String tmcDescription = rs.getString("descr");
                int balance = rs.getInt("store_c");

                Tmc tmc = new Tmc(idTmc, tmcDescription, balance);

                result.add(tmc);
            }
            log.debug("Was read {} Tmc from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Tmc'.", e);
        }
        log.debug("Tmc not found.");
        return null;
    }

}
