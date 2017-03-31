package dao;

import airplane.Airplane;
import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import flight.Flight;
import flight.Flights;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mao on 2017/2/22.
 */
public class DaoFlight {
    /**
     * Builds collection of flights from flights described in XML
     *
     * Parses an XML string to read each of the flights and adds each valid flight
     * to the collection. The method uses Java DOM (Document Object Model) to convert
     * from XML to Java primitives.
     *
     * @param xmlFlights XML string containing set of flights
     * @return [possibly empty] collection of Flights in the xml string
     * @throws NullPointerException included to keep signature consistent with other addAll methods
     *
     * @pre the xmlFlights string adheres to the format specified by the server API
     * @post the [possibly empty] set of Flights in the XML string are added to collection
     */
    public static Flights addAll (String xmlFlights) throws NullPointerException {
        Flights flights = new Flights();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each flight to our collection
        Document docFlights = DomUtil.buildDomDoc(xmlFlights);
        NodeList nodesFlights = docFlights.getElementsByTagName("Flight");

        for (int i = 0; i < nodesFlights.getLength(); i++) {
            Element elementFlight = (Element) nodesFlights.item(i);
            Flight flight = buildFlight (elementFlight);

            if (flight.isValid()) {
                flights.add(flight);
            }
        }
        return flights;
    }

    /**
     * Creates an Flight object from a DOM node
     *
     * Processes a DOM Node that describes an Flight and creates an Flight object from the information
     * @param nodeFlight is a DOM Node describing an Flight
     * @return Flight object created from the DOM Node representation of the Flight
     *
     * @pre nodeFlight is of format specified by CS509 server API
     */
    static private Flight buildFlight (Node nodeFlight) {
        /**
         * Instantiate an empty Flight object
         */
        Flight flight = new Flight();

        Airplane airplane;
        int flightTime;
        String number;
        Airport departAirport;
        Date departTime = null;
        Airport arrivalAirport;
        Date arrivalTime = null;
        double firstClassPrice;
        int firstClassSeats;
        double coachClassPrice;
        int coachClassSeats;

        // The flight element has attributes of Name and 3 character flight code
        Element elementFlight = (Element) nodeFlight;

        Airplanes airplanes = Airplanes.getInstance();
        airplane = airplanes.getAirplaneByModel(elementFlight.getAttributeNode("Airplane").getValue());
        flightTime = Integer.parseInt(elementFlight.getAttributeNode("FlightTime").getValue());
        number = elementFlight.getAttributeNode("Number").getValue();

        Element elementAttr;
        Airports airports = Airports.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm zzz");

        Element elementDeparture = (Element)elementFlight.getElementsByTagName("Departure").item(0);
        elementAttr = (Element)elementDeparture.getElementsByTagName("Code").item(0);
        departAirport = airports.getAirportByCode(DomUtil.getCharacterDataFromElement(elementAttr));
        elementAttr = (Element)elementDeparture.getElementsByTagName("Time").item(0);
        try {
            departTime = sdf.parse(DomUtil.getCharacterDataFromElement(elementAttr));
        } catch (ParseException e) {

        }

        Element elementArrival = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
        elementAttr = (Element)elementArrival.getElementsByTagName("Code").item(0);
        arrivalAirport = airports.getAirportByCode(DomUtil.getCharacterDataFromElement(elementAttr));
        elementAttr = (Element)elementArrival.getElementsByTagName("Time").item(0);
        try {
            arrivalTime = sdf.parse(DomUtil.getCharacterDataFromElement(elementAttr));
        } catch (ParseException e) {

        }

        Element elementSeating = (Element)elementFlight.getElementsByTagName("Seating").item(0);

        Element elementFirstClass = (Element)elementSeating.getElementsByTagName("FirstClass").item(0);
        String price = elementFirstClass.getAttributeNode("Price").getValue().substring(1);
        price = price.replace(",", "");
        firstClassPrice = Double.parseDouble(price.substring(1));
        firstClassSeats = Integer.parseInt(DomUtil.getCharacterDataFromElement(elementFirstClass));

        Element elementCoachClass = (Element)elementSeating.getElementsByTagName("Coach").item(0);
        price = elementCoachClass.getAttributeNode("Price").getValue().substring(1);
        price = price.replace(",", "");
        coachClassPrice = Double.parseDouble(price.substring(1));
        coachClassSeats = Integer.parseInt(DomUtil.getCharacterDataFromElement(elementCoachClass));
        // The latitude and longitude are child elements

        /**
         * Update the Flight object with values from XML node
         */
        flight.setAirplane(airplane);
        flight.setFlightTime(flightTime);
        flight.setNumber(number);
        flight.setDepartAirport(departAirport);
        flight.setDepartTime(departTime);
        flight.setArrivalAirport(arrivalAirport);
        flight.setArrivalTime(arrivalTime);
        flight.setFirstClassPrice(firstClassPrice);
        flight.setFirstClassSeats(firstClassSeats);
        flight.setCoachClassPrice(coachClassPrice);
        flight.setCoachClassSeats(coachClassSeats);

        return flight;
    }
}
