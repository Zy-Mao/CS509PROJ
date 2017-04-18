package com.wpi.teamd.airplane;

/**
 * Created by Mao on 17/3/19.
 */
public class Airplane {
    private String manufacture;
    private String model;
    private int firstClassSeat;
    private int coachClassSeat;

    public Airplane() {

    }

    public Airplane(String manufacture, String model, int firstClassSeat, int coachClassSeat) {
        this.manufacture = manufacture;
        this.model = model;
        this.firstClassSeat = firstClassSeat;
        this.coachClassSeat = coachClassSeat;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(manufacture).append(", ");
        stringBuffer.append(model).append(", ");
        stringBuffer.append(firstClassSeat).append(", ");
        stringBuffer.append(coachClassSeat);

        return stringBuffer.toString();
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFirstClassSeat() {
        return firstClassSeat;
    }

    public void setFirstClassSeat(int firstClassSeat) {
        this.firstClassSeat = firstClassSeat;
    }

    public int getCoachClassSeat() {
        return coachClassSeat;
    }

    public void setCoachClassSeat(int coachClassSeat) {
        this.coachClassSeat = coachClassSeat;
    }

    public boolean isValid() {
        if ((manufacture == null) || (manufacture == ""))
            return false;
        if ((model == null) || (model == ""))
            return false;
        if (firstClassSeat <= 0 || coachClassSeat <= 0)
            return false;

        return true;
    }
}
