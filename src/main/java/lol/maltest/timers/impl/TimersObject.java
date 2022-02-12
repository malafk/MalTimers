package lol.maltest.timers.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * Timers class for caching timers
 */
public class TimersObject {

    private String id;
    private long time;
    private final long made;
    private final UUID author;

    /**
     * Create an object for a timer
     * @param time   The time of the timer
     * @param made
     * @param author
     */
    public TimersObject(String id, long time, long made, UUID author) {
        this.id = id;
        this.time = time;
        this.made = made;
        this.author = author;
    }

    /**
     * Returns time as a full date E.G: 7d 2h 10m 29s
     * @return full date as a string
     */
    public String toFullDate() {
        Duration duration = getDuration();
        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds() ;
        return
                (days ==  0?"":days+"d, ")+
                        (hours == 0?"":hours+"h, ")+
                        (minutes ==  0?"":minutes+"m, ")+
                        seconds+"s ";
    }

    /**
     * Returns the time as a timer countdown E.G: 7:2:10:29
     * @return full date as a timer
     */
    public String toTimer() {
        Duration duration = getDuration();
        long days = duration.toDays();
        duration = duration.minusDays(days);
        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds() ;
        return
                (days ==  0?"0:":days+":")+
                        (hours == 0?"0:":hours+":")+
                        (minutes ==  0?"0:":minutes+":")+
                        (seconds == 0?"0:":seconds);
    }

    /**
     * Get the time as a unix timestamp
     * @return the unix timestamp
     */
    public long getUnixTime() {
        return time;
    }

    /**
     * A method to get the duration
     * @return the time between as a {@link Duration}
     */
    public Duration getDuration() {
        long timeNow = System.currentTimeMillis() / 1000;
        Instant one = Instant.ofEpochSecond(time);
        Instant two = Instant.ofEpochSecond(timeNow);
        return Duration.between(two, one);
    }

    /**
     * Get the time left of the timer
     * @return time left as unix
     */
    public long getTimeLeft() {
        return getUnixTime() - System.currentTimeMillis() / 1000;
    }

    /**
     * Returns the Id of the timer
     * @return id of the timer
     */
    public String getId() {
        return id;
    }

    /**
     * Set the new time for the timer (goal)
     * @param time the new unix timestamp
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Checks if a timer has completed by checking if the time left is below or equal to 0
     * @return if timer past the set unix timestamp
     */
    public boolean hasCompleted() {
        return getTimeLeft() <= 0;
    }
}
