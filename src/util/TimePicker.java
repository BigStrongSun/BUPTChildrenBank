package util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * <p>The util.TimePicker class represents a time selection dialog window. It allows the user
 * to select a specific date and time using dropdown menus for month, day, hour, and minute.
 * The selected time can be obtained through a callback listener.
 */
public class TimePicker extends JFrame {
    private JComboBox<Integer> monthComboBox;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> hourComboBox;
    private JComboBox<Integer> minuteComboBox;
    private LocalDateTime currentTime = LocalDateTime.now();
    private int currentYear = currentTime.getYear();
    private TimeSelectionListener timeSelectionListener;

    /**
     * Constructs a util.TimePicker dialog window.
     */
    public TimePicker() {
        setTitle("Time Picker");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);

        // Month
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(40, 20, 50, 20);
        getContentPane().add(monthLabel);

        monthComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        monthComboBox.setSelectedItem(currentTime.getMonthValue());
        monthComboBox.setBounds(100, 20, 50, 20);
        monthComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDays();
            }
        });
        getContentPane().add(monthComboBox);

        // Day
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(160, 20, 30, 20);
        getContentPane().add(dayLabel);

        dayComboBox = new JComboBox<>();
        updateDays(); // 初始化天数
        dayComboBox.setSelectedItem(currentTime.getDayOfMonth());
        dayComboBox.setBounds(200, 20, 50, 20);
        getContentPane().add(dayComboBox);

        // Hour
        JLabel hourLabel = new JLabel("Hour:");
        hourLabel.setBounds(40, 50, 50, 20);
        getContentPane().add(hourLabel);

        hourComboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            hourComboBox.addItem(i);
        }
        hourComboBox.setSelectedItem(currentTime.getHour());
        hourComboBox.setBounds(100, 50, 50, 20);
        getContentPane().add(hourComboBox);

        // Minute
        JLabel minuteLabel = new JLabel("Minute:");
        minuteLabel.setBounds(160, 50, 50, 20);
        getContentPane().add(minuteLabel);

        minuteComboBox = new JComboBox<>();
        for (int i = 0; i < 60; i++) {
            minuteComboBox.addItem(i);
        }
        minuteComboBox.setSelectedItem(currentTime.getMinute());
        minuteComboBox.setBounds(220, 50, 50, 20);
        getContentPane().add(minuteComboBox);

        JButton selectButton = new JButton("Confirm");
        selectButton.setBounds(125, 100, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int month = (int) monthComboBox.getSelectedItem();
                int day = (int) dayComboBox.getSelectedItem();
                int hour = (int) hourComboBox.getSelectedItem();
                int minute = (int) minuteComboBox.getSelectedItem();

                LocalDateTime selectedTime = LocalDateTime.now()
                        .withMonth(month)
                        .withDayOfMonth(day)
                        .withHour(hour)
                        .withMinute(minute);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String result = selectedTime.format(formatter);
                System.out.println(result);
                if (timeSelectionListener != null) {
                    timeSelectionListener.onTimeSelected(result);
                    dispose();
                }
            }
        });
        getContentPane().add(selectButton);
    }

    private void updateDays() {
        int selectedMonth = (int) monthComboBox.getSelectedItem();
        int daysInMonth = YearMonth.of(currentYear, selectedMonth).lengthOfMonth();
        dayComboBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            dayComboBox.addItem(i);
        }
    }

    public void setTimeSelectionListener(TimeSelectionListener listener) {
        timeSelectionListener = listener;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimePicker timePicker = new TimePicker();
                timePicker.setVisible(true);
            }
        });
    }
}
