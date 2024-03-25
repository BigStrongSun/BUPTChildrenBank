package util;

/**
 * The util.TimeSelectionListener interface provides a callback mechanism for notifying listeners
 * when a time is selected.
 *
 * @author Yuxinyue Qian
 */
public interface TimeSelectionListener {
    /**
     * Called when a time is selected.
     *
     * @param time The selected time as a String.
     */
    void onTimeSelected(String time);
}