package ua.aleks4ay.manufplan.domain.dao_dbf;

import java.io.*;
import com.linuxense.javadbf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.manufplan.domain.model.Journal;
import ua.aleks4ay.manufplan.domain.tools.TimeConverter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ua.aleks4ay.manufplan.log.ClassNameUtil.getCurrentClassName;

public class JournalReader {

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String DBF_PATH = DbfTools.getDbfPath();

    public static void main(String[] args) {
        Map<String, Journal> journalMap = new JournalReader().getAllJournal();
        for (Journal c : journalMap.values()) {
            System.out.println(c.getIdDoc() + ", " + c.getDocNumber() + ", " + c.getDateCreate());
        }
        System.out.println(journalMap.size());
    }

    public Map<String, Journal> getAllJournal() {
        Map<String, Journal> mapJournal = new HashMap<>();
        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(DBF_PATH + "1SJOURN.DBF"));

            DBFRow row;
            while ((row = reader.nextRow()) != null) {
                Date keyOrderYear = row.getDate("DATE");
                int keyOrderIsEnable = row.getInt("CLOSED");
                if (keyOrderYear.getTime() < 1560805200000L || keyOrderIsEnable ==4 ) {
                    continue;
                }

                String idDoc = row.getString("IDDOC");
                String docNumber = new String(row.getString("DOCNO").getBytes("ISO-8859-15"), "Windows-1251");
                long dateCreate = row.getDate("DATE").getTime();
                long timeCreate = TimeConverter.convertStrToTimeMillisecond(row.getString("TIME"));

                Journal journal = new Journal(idDoc, docNumber, new Timestamp(dateCreate + timeCreate));

                mapJournal.put(idDoc, journal);

            }
            log.debug("Was read {} Journal from 1C '1SJOURN'.", mapJournal.size());
            return mapJournal;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file '1SJOURN.dbf'.", e);
        } catch (Exception e) {
            log.warn("Exception during writing all 'Journal'.", e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("Journal not found.");
        return null;
    }
}
