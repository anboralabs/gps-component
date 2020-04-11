package co.anbora.component.location;

public class LocationUpdate {

  public static final int PRIORITY_HIGH_ACCURACY = 100;
  public static final int PRIORITY_BALANCED_POWER_ACCURACY = 102;
  public static final int PRIORITY_LOW_POWER = 104;
  public static final int PRIORITY_NO_POWER = 105;

  private final int MILLI_SECONDS = 1000;

  private int interval;
  private int fastInterval;
  private int priority;

  public LocationUpdate(int interval, int fastInterval, int priority) {
    this.interval = interval * MILLI_SECONDS;
    this.fastInterval = fastInterval * MILLI_SECONDS;
    this.priority = priority;
  }

  public int getInterval() { return interval; }

  public int getFastInterval() { return fastInterval; }

  public int getPriority() { return priority; }
}
