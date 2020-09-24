package ua.aleks4ay.manufplan.domain.dao_dbf;

import com.linuxense.javadbf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class EmbodimentReader  {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public Map<String, String> getAll() {
        Map<String, String> mapEmbodiment = new HashMap<>();

        DBFReader embodimentReader = null;
        try {
            embodimentReader = new DBFReader(new FileInputStream(DBF_PATH + "SC14716.DBF"));

            DBFRow embodimentRow;
            while ((embodimentRow = embodimentReader.nextRow()) != null) {
                String id = embodimentRow.getString("ID");
                String descr = new String(embodimentRow.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
                mapEmbodiment.put(id, descr);
            }
            log.debug("Was read {} rows from 1C SC14716.", mapEmbodiment.size());
            return mapEmbodiment;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'SC14716.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during reading all rows 'Embodiment'.", e);
        } finally {
            DBFUtils.close(embodimentReader);
        }
        log.debug("Embodiment not found.");
        return null;
    }
}
