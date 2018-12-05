import java.util.ArrayList;
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
    private static List<String> fileContent;
    private List<String> firstHalves;
    private List<String> secondHalves;
    private String filePath;
    private Integer count;
    private List<String> eventsLoop;


    private static Messages instance = null;

    public static List<String> getFileContent() {
        return fileContent;
    }

    public static void setFileContent(List<String> fileContent) {
        Messages.fileContent = fileContent;
    }

    public List<String> getFirstHalves() {
        return firstHalves;
    }

    public void setFirstHalves(List<String> firstHalves) {
        this.firstHalves = firstHalves;
    }

    public List<String> getSecondHalves() {
        return secondHalves;
    }

    public void setSecondHalves(List<String> secondHalves) {
        this.secondHalves = secondHalves;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getEventsLoop() {
        return eventsLoop;
    }

    public void setEventsLoop(List<String> eventsLoop) {
        this.eventsLoop = eventsLoop;
    }

    public static void setInstance(Messages instance) {
        Messages.instance = instance;
    }

    private Messages() {
        fileContent = new ArrayList<>();
        firstHalves = new ArrayList<>();
        secondHalves = new ArrayList<>();
        filePath = "data/Akka10.txt";
        count = 0;
        eventsLoop = new ArrayList<>();
    }

    public static Messages getInstance() {
        if(instance == null) {
            instance = new Messages();
        }
        return instance;
    }
}