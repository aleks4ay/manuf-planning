package ua.aleks4ay.manufplan.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.dao.DescriptionDao;
import ua.aleks4ay.manufplan.domain.dao.UtilDao;
import ua.aleks4ay.manufplan.domain.model.Description;

import java.sql.Connection;
import java.util.List;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class DescriptionReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("Start writing 'D E S C R I P T I O N'.");

        List<Description> descriptions = new DescriptionReader().getAll();
        descriptions.forEach(System.out::println);

        long end = System.currentTimeMillis();
        log.info("End writing 'D E S C R I P T I O N'. Time = {} c. ", (double)(end-start)/1000);
    }


    public List<Description> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        DescriptionDao descriptionDao = new DescriptionDao(connPostgres);

        List<Description> descriptionList = descriptionDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Descriptions.", descriptionList.size());

        return descriptionList;
    }

}
