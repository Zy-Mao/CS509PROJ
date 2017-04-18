package com.wpi.teamd.view;

import com.wpi.teamd.airport.Airport;
import com.wpi.teamd.airport.Airports;
import com.wpi.teamd.dao.ServerInterface;
import com.wpi.teamd.flight.Flight;
import com.wpi.teamd.flight.Flights;
//import org.jdatepicker.impl.JDatePanelImpl;
//import org.jdatepicker.impl.JDatePickerImpl;
//import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * Created by Mao on 17/3/23.
 */
public class FlightSearchFrame extends JFrame {
    private JPanel contentPanel;
    private JComboBox departureComboBox, arrivalComboBox;
    private JButton searchButton;
    private JList flightList;
    private JScrollPane flightListScrollPane;
//    private JDatePickerImpl datePicker;
    private DefaultListModel listModel;


    public FlightSearchFrame(int x, int y) {
        super();
        this.setTitle("World Plane, Inc. Retail Customer Airline Reservation System");
        this.setContentPane(getContentPanel());
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(x, y, 700, 500);
//        this.setBackground(Color.WHITE);
        this.validate();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private JPanel getContentPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();

            contentPanel.setLayout(null);
            contentPanel.add(getDepartureComboBox());
            contentPanel.add(getArrivalComboBox());
//            contentPanel.add(getDatePicker());
            contentPanel.add(getSearchButton());
            contentPanel.add(getFlightListScrollPane());
        }
        return contentPanel;
    }

    private JComboBox getDepartureComboBox() {
        if (departureComboBox == null) {
            departureComboBox = new JComboBox();
            departureComboBox.setBounds(new Rectangle(120, 12, 80,25));
            Airports airports = Airports.getInstance();
            for (Airport airport : airports) {
                departureComboBox.addItem(airport.code());
            }
            departureComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event) {
                    int index = departureComboBox.getSelectedIndex();
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("Select Departure " + index + " " + event.getItem());
                    }
                }
            });
        }
        return departureComboBox;
    }

    private JComboBox getArrivalComboBox() {
        if (arrivalComboBox == null) {
            arrivalComboBox = new JComboBox();
            arrivalComboBox.setBounds(new Rectangle(215,12,80,25));
            Airports airports = Airports.getInstance();
            for (Airport airport : airports) {
                arrivalComboBox.addItem(airport.code());
            }
            arrivalComboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent event) {
                    int index = arrivalComboBox.getSelectedIndex();
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        System.out.println("Select Arrival " + index + " " + event.getItem());
                    }
                }
            });
        }
        return arrivalComboBox;
    }

    private JButton getSearchButton() {
        if (searchButton == null) {
            searchButton = new JButton();
            searchButton.setBounds(new Rectangle(525,12,70,25));
            searchButton.setText("Search");
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getDepartureComboBox().getSelectedIndex()
                            == getArrivalComboBox().getSelectedIndex()) {
                        alertMessage("Fail", "Same", 0);
                    } else {
                        Airport departAirport
                                = Airports.getInstance().get(getDepartureComboBox().getSelectedIndex());
                        Airport arrivalAirport
                                = Airports.getInstance().get(getArrivalComboBox().getSelectedIndex());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
//                        try {
//                            date = sdf.parse(getDatePicker().getJFormattedTextField().getText());
//                        } catch (ParseException ex) {
//
//                        }
                        Flights flights = ServerInterface.getFlights(departAirport.code(), date, true);

                        getListModel().clear();

                        for (Flight flight: flights) {
                            if (flight.getArrivalAirport().equals(arrivalAirport)) {
                                System.out.println(flight.toString());
                                getListModel().addElement(flight.toString());
                            }
                        }
                    }


                }
            });
        }
        return searchButton;
    }

    private JList getFlightList() {
        if (flightList == null) {
            flightList = new JList(getListModel());
            flightList.setBounds(new Rectangle(120,60,450, 200));
            flightList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        }
        return flightList;
    }

    private JScrollPane getFlightListScrollPane() {
        if (flightListScrollPane == null) {
            flightListScrollPane = new JScrollPane(getFlightList());
            flightListScrollPane.setBounds(new Rectangle(120,60,450, 200));
//            flightListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        }
        return flightListScrollPane;
    }

    private DefaultListModel getListModel() {
        if (listModel == null) {
            listModel = new DefaultListModel();
        }
        return listModel;
    }

//    private JDatePickerImpl getDatePicker() {
//        if (datePicker == null) {
//            UtilDateModel model = new UtilDateModel();
//            Properties properties = new Properties();
//            properties.put("text.today", "Today");
//            properties.put("text.month", "Month");
//            properties.put("text.year", "Year");
//            model.setDate(2017, 4, 5);
//            model.setSelected(true);
//            JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
////            JDatePanelImpl datePanel = new JDatePanelImpl(model);
//            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
//            datePicker.setBounds(new Rectangle(310,10,200,30));
//        }
//        return datePicker;
//    }

    private class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private void alertMessage(String title, String value, int i) {
        JOptionPane.showMessageDialog(this, value, title, i);
    }



}
