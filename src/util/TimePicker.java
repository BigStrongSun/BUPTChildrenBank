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
 *
 * @author Yuxinyue Qian
 */
public class TimePicker extends JFrame {
    private TimePicker timePicker;
    private JComboBox<Integer> dayComboBox;
    private JComboBox<Integer> hourComboBox;
    private JComboBox<Integer> minuteComboBox;
    private LocalDateTime currentTime = LocalDateTime.now();
    private int currentYear = currentTime.getYear();
    private int currentMonth = currentTime.getMonthValue();
    private int daysInMonth = YearMonth.of(currentYear, currentMonth).lengthOfMonth();
    private TimeSelectionListener timeSelectionListener;

    /**
     * Constructs a util.TimePicker dialog window.
     */
    public TimePicker() {
        setTitle("时间选择器");
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);

        //月
        JLabel monthLabel = new JLabel(currentMonth + " 月");
        monthLabel.setBounds(40, 20, 40, 20);
        getContentPane().add(monthLabel);

        //日
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= daysInMonth; i++) {
            dayComboBox.addItem(i);
        }
        dayComboBox.setBounds(80, 20, 40, 20);
        getContentPane().add(dayComboBox);
        JLabel dayLabel = new JLabel("日");
        dayLabel.setBounds(120, 20, 40, 20);
        getContentPane().add(dayLabel);

        //小时
        hourComboBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) {
            hourComboBox.addItem(i);
        }
        hourComboBox.setBounds(140, 20, 40, 20);
        getContentPane().add(hourComboBox);
        JLabel hourLabel = new JLabel("时");
        hourLabel.setBounds(180, 20, 40, 20);
        getContentPane().add(hourLabel);

        //分钟
        minuteComboBox = new JComboBox<>();
        for (int i = 0; i < 60; i++) {
            minuteComboBox.addItem(i);
        }
        minuteComboBox.setBounds(200, 20, 40, 20);
        getContentPane().add(minuteComboBox);
        JLabel minuteLabel = new JLabel("分");
        minuteLabel.setBounds(240, 20, 40, 20);
        getContentPane().add(minuteLabel);

        // 设置下拉框的默认选项为当前时间
        dayComboBox.setSelectedItem(currentTime.getDayOfMonth());
        hourComboBox.setSelectedItem(currentTime.getHour());
        minuteComboBox.setSelectedItem(currentTime.getMinute());

        JButton selectButton = new JButton("Confirm");
        selectButton.setBounds(95, 100, 100, 30);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int day = (int) dayComboBox.getSelectedItem();
                int hour = (int) hourComboBox.getSelectedItem();
                int minute = (int) minuteComboBox.getSelectedItem();

                LocalDateTime selectedTime = LocalDateTime.now()
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
