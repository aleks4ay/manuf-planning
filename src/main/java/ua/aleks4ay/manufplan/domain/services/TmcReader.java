package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.TmcDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.new_model.Tmc;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class TmcReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("Start writing 'T M C'.");

        List<Tmc> descriptions = new TmcReader().getAllAsList();
        descriptions.forEach(System.out::println);

        long end = System.currentTimeMillis();
        log.info("End writing 'T M C'. Time = {} c. ", (double)(end-start)/1000);
    }


    public List<Tmc> getAllAsList() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        TmcDao tmcDao = new TmcDao(connPostgres);

        List<Tmc> tmcList = tmcDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} TMC.", tmcList.size());

        return tmcList;
    }

    public Map<String, Tmc> getAllAsMap() {
        List<Tmc> result = getAllAsList();
        return result
                .stream()
                .collect(Collectors.toMap(Tmc::getId, Tmc::getTmc));
    }
}
