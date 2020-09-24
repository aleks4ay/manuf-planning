package ua.aleks4ay.manufplan.domain.dao_dbf;

import com.linuxense.javadbf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class WorkerReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, String> workers = new WorkerReader().getAll();
    }

    public Map<String, String> getAll() {
        Map<String, String> mapWorker = new HashMap<>();
        mapWorker.put("     0", "-");

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "SC1670.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                String id = row.getString("ID");
                String name = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");

                mapWorker.put(id, name);
            }
            log.debug("Was read {} Worker from 1S.", mapWorker.size());
            return mapWorker;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file 'SC1670.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Worker'." , e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Worker not found.");
        return null;
    }
}
