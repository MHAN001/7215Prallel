import org.omg.CORBA.PRIVATE_MEMBER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Messages that are passed around the actors are usually immutable classes.
 * Think how you go about creating immutable classes:) Make them all static
 * classes inside the Messages class.
 * 
 * This class should have all the immutable messages that you need to pass
 * around actors. You are free to add more classes(Messages) that you think is
 * necessary
 * 
 * @author akashnagesh
 *
 */
public final class Messages {
    private StringBuffer fileContent;
//    private HashMap<Integer, StringBuffer> textList;
    private int fileAmount;
    private String fileName;


    public static class EstimatorRes{
        private double Ct;
        private double Avg;

        public EstimatorRes(double ct, double avg) {
            Ct = ct;
            Avg = avg;
        }

        public double getCt() {
            return Ct;
        }

        public double getAvg() {
            return Avg;
        }
    }

    public static class CounterRes{
        private double Ut;

        public CounterRes(double ut) {
            Ut = ut;
        }

        public double getUt() {
            return Ut;
        }
    }

    public StringBuffer getFileContent() {
        return fileContent;
    }

    public void setFileContent(StringBuffer fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static Messages getInstance() {
        return instance;
    }

    public int getFileAmount() {
        return fileAmount;
    }

    public void setFileAmount(int fileAmount) {
        this.fileAmount = fileAmount;
    }

    private static Messages instance = null;

    public static void setInstance(Messages instance) {
        Messages.instance = instance;
    }

    public Messages(StringBuffer fileContent, int fileAmount, String fileName) {
        this.fileContent = fileContent;
        this.fileAmount = fileAmount;
        this.fileName = fileName;
    }
}