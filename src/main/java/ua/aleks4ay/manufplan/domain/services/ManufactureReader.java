package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.ManufactureDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.BigTmc;
import ua.aleks4ay.manufplan.domain.model.ManufactureTechno;
import ua.aleks4ay.manufplan.domain.tools.DateConverter;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class ManufactureReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());


    public List<ManufactureTechno> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        ManufactureDao manufactureDao = new ManufactureDao(connPostgres);
//        TmcDao tmcDao = new TmcDao(connPostgres);

        List<ManufactureTechno> manufactureTechnoList = manufactureDao.getAll();
        Map<String, BigTmc> tmcMap = new BigTmcReader().getBigTmcAsMap();
        for (ManufactureTechno manufacture : manufactureTechnoList) {
            String key = manufacture.getIdTmc();
            manufacture.setTmcDescr(tmcMap.get(key).getTmcDescription());
        }

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} ManufactureTechno.", manufactureTechnoList.size());

        return manufactureTechnoList;
    }

    public List<ManufactureTechno> getById(String idTmc) {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        ManufactureDao manufactureDao = new ManufactureDao(connPostgres);

        List<ManufactureTechno> manufactureTechnoList = manufactureDao.getByIdTmc(idTmc);
        if (manufactureTechnoList == null) {
            manufactureTechnoList = Collections.EMPTY_LIST;
        }
        utilDao.closeConnection(connPostgres);
        log.info("Was read {} ManufactureTechno.", manufactureTechnoList.size());

        return manufactureTechnoList;
    }

    public void sortByDate(List<ManufactureTechno> manufactureTechnoList) {
        Comparator<ManufactureTechno> comparatorManufactureTechno = (m1, m2) ->
                m1.getTimeManufacture().compareTo(m2.getTimeManufacture());
        Collections.sort(manufactureTechnoList, comparatorManufactureTechno);
    }

    public List<ManufactureTechno> removeWithOldDate(List<ManufactureTechno> manufactureTechnoList, Timestamp minDateToShipment) {
        List<ManufactureTechno> result = new ArrayList<>();
        for (ManufactureTechno manufactureTechno : manufactureTechnoList) {
            if (manufactureTechno.getTimeManufacture().before(minDateToShipment)) {
                continue;
            }
            result.add(manufactureTechno);
        }
        return result;
    }

    public List<ManufactureTechno> restOnlyThisDate(List<ManufactureTechno> manufactureTechnoList, Timestamp minDateToShipment) {
        List<ManufactureTechno> result = new ArrayList<>();
        for (ManufactureTechno manufactureTechno : manufactureTechnoList) {
            boolean isEqualsDays = DateConverter.isTheSameDay(manufactureTechno.getTimeManufacture(), minDateToShipment);

            if (isEqualsDays) {
                result.add(manufactureTechno);
            }
        }
        return result;
    }
}
