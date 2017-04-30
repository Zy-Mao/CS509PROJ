package com.wpi.teamd.entity;

/**
 * This class holds values pertaining to a single Airplane. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and
 * setter methods.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-03-19
 *
 *
 *
 */
public class Airplane {
	/**
	 * Airport attributes as defined by the CS509 server interface XML
	 */
	private String manufacture;
	private String model;
	private int firstClassSeat;
    private int coachClassSeat;

	/**
	 * Default constructor
	 * <p>
	 * Constructor without params. Requires object fields to be explicitly
	 * set using setter methods
	 *
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */
	public Airplane() {

	}

	/**
	 * Initializing constructor.
	 * <p>
	 * All attributes are initialized with input values
	 *
	 * @param manufacture
	 * @param model
	 * @param firstClassSeat
	 * @param coachClassSeat
	 * @throws IllegalArgumentException is any parameter is invalid
	 */
	public Airplane(String manufacture, String model, int firstClassSeat, int coachClassSeat) {
		this.manufacture = manufacture;
		this.model = model;
		this.firstClassSeat = firstClassSeat;
		this.coachClassSeat = coachClassSeat;
	}

	/**
	 * @return the object formatted as String to display
	 */
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
		return !(firstClassSeat <= 0 || coachClassSeat <= 0);
	}
}
