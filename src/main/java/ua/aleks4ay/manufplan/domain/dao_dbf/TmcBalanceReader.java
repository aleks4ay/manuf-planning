package ua.aleks4ay.manufplan.domain.dao_dbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class TmcBalanceReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, Integer> tmcBalanceMap = new TmcBalanceReader().getAll();
//        for (String key : tmcBalanceMap.keySet()) {
//            System.out.println(key + ": " + tmcBalanceMap.get(key) + " шт.");
//        }
    }

    public Map<String, Integer> getAll() {
        Map<String, Integer> tmcBalanceMap = new HashMap<>();
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "RG1253.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                if (! row.getString("SP4643").equals("     C")) {
                    continue;
                }
                String idTmc = row.getString("SP1249");
                Integer count = row.getInt("SP1251");

                tmcBalanceMap.put(idTmc, count);

            }
            log.debug("Was read {} TMC Balance from 1C 'RG1253'.", tmcBalanceMap.size());

            return tmcBalanceMap;

        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'RG1253.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'TMC Balance'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("TMC Balance not found.");
        return null;
    }
}
