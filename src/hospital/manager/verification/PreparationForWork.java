package hospital.manager.verification;

import hospital.download.threads.ThreadDonwload;
import hospital.download.threads.ThreadParsing;
import hospital.parse.petient.XmlConverter;
import hospital.manager.Manager;

import java.io.File;

/**
 * Методам этого класса суждено подготовить программу к работе.
 * Загрузка информации о госпитале для дальнейшей манипуляции с ней
 */
public class PreparationForWork {
    private static PreparationForWork preparationForWork;

    private PreparationForWork() {

    }

    public static PreparationForWork newInstance() {
        if (preparationForWork == null) {
            preparationForWork = new PreparationForWork();
        }
        return preparationForWork;
    }

    public void downloadAndParsingHospital() {
        ThreadDonwload threadDonwload = new ThreadDonwload();
        ThreadParsing threadParsing = new ThreadParsing();

        threadDonwload.setThreadParsing(threadParsing);
        threadParsing.setThreadDonwload(threadDonwload);

        threadParsing.start();
        threadDonwload.start();
        try {
            threadParsing.join();
            threadDonwload.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkAutomaticalPreparation("Данные готовы, запуск программы");
    }

    public void checkAutomaticalPreparation(String massage){
        if(new File("hospital.xml").exists() && new File("hospital.json").exists()){
            Manager.getInstance().imitationOfAGoodProgramm(massage);

        }
    }

    public void uploadPatientHistory() {
        if (new File("patient.xml").exists()) {
            XmlConverter xmlConverter = new XmlConverter();
            xmlConverter.convertFromXml();
            xmlConverter = null;
        }

    }

    public static PreparationForWork removePreparationForWork() {
        preparationForWork = null;
        return null;
    }
}