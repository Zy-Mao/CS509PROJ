package com.wpi.teamd.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mao on 17/3/19.
 */
public class Flight {
    private Airplane airplane;
    private int flightTime;
    private String number;
    private Airport departAirport;
    private Date departTime;
    private Airport arrivalAirport;
    private Date arrivalTime;
    private double firstClassPrice;
    private int firstClassSeats;
    private double coachClassPrice;
    private int coachClassSeats;

    public Flight() {

    }

    public Flight(Airplane airplane, int flightTime, String number,
                  Airport departAirport, Date departTime,
                  Airport arrivalAirport, Date arrivalTime,
                  double firstClassPrice, int firstClassSeats,
                  double coachClassPrice, int coachClassSeats) {
        this.airplane = airplane;
        this.flightTime = flightTime;
        this.number = number;
        this.departAirport = departAirport;
        this.departTime = departTime;
        this.arrivalAirport = arrivalAirport;
        this.arrivalTime = arrivalTime;
        this.firstClassPrice = firstClassPrice;
        this.firstClassSeats = firstClassSeats;
        this.coachClassPrice = coachClassPrice;
        this.coachClassSeats = coachClassSeats;
    }

    public boolean isValid() {
        if (airplane == null || !airplane.isValid())
            return false;
        if (flightTime < 0 || number == null)
            return false;
        if (departAirport == null || !departAirport.isValid() || departTime == null)
            return false;
        if (arrivalAirport == null || !arrivalAirport.isValid() || arrivalTime == null)
            return false;
        if (firstClassPrice < 0 || firstClassSeats < 0)
            return false;
        return !(coachClassPrice < 0 || coachClassSeats < 0);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        sb.append("Flight#").append(number).append(", ");
        sb.append(airplane.getModel()).append(", ");
        sb.append(departAirport.code()).append(", ");
        sb.append(sdf.format(departTime)).append(", ");
        sb.append(arrivalAirport.code()).append(", ");
        sb.append(sdf.format(arrivalTime)).append(", ");
        sb.append("$").append(firstClassPrice).append(", ");
        sb.append(firstClassSeats).append(", ");
        sb.append("$").append(coachClassPrice).append(", ");
        sb.append(coachClassSeats);

        return sb.toString();
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Airport getDepartAirport() {
        return departAirport;
    }

    public void setDepartAirport(Airport departAirport) {
        this.departAirport = departAirport;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getFirstClassPrice() {
        return firstClassPrice;
    }

    public void setFirstClassPrice(double firstClassPrice) {
        this.firstClassPrice = firstClassPrice;
    }

    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setFirstClassSeats(int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }

    public double getCoachClassPrice() {
        return coachClassPrice;
    }

    public void setCoachClassPrice(double coachClassPrice) {
        this.coachClassPrice = coachClassPrice;
    }

    public int getCoachClassSeats() {
        return coachClassSeats;
    }

    public void setCoachClassSeats(int coachClassSeats) {
        this.coachClassSeats = coachClassSeats;
    }

    public int getSeatsInfoByClass(Integer seatClass) {
        return seatClass == 1 ? this.getFirstClassSeats() : this.getCoachClassSeats();
    }

    public long getDurationInSecond() {
        return (getArrivalTime().getTime() - getDepartTime().getTime()) / 1000;
    }

    public String getDurationInString() {
        long duration = this.getDurationInSecond();
        return (duration / 3600) + " hour " + ((duration / 60) % 60) + " min";
    }
}
