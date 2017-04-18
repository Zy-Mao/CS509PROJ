package com.wpi.teamd.dao;

import com.wpi.teamd.airplane.Airplane;
import com.wpi.teamd.airplane.Airplanes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by Mao on 17/3/19.
 */
public class DaoAirplane {
    /**
     * Builds collection of airplanes from airplanes described in XML
     *
     * Parses an XML string to read each of the airplanes and adds each valid airplane
     * to the collection. The method uses Java DOM (Document Object Model) to convert
     * from XML to Java primitives.
     *
     * @param xmlAirplanes XML string containing set of airplanes
     * @return [possibly empty] collection of Airplanes in the xml string
     * @throws NullPointerException included to keep signature consistent with other addAll methods
     *
     * @pre the xmlAirplanes string adheres to the format specified by the server API
     * @post the [possibly empty] set of Airplanes in the XML string are added to collection
     */
    public static Airplanes addAll (String xmlAirplanes) throws NullPointerException {
//        Airplanes airplanes = new Airplanes();
        Airplanes airplanes = Airplanes.getInstance();
        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each airplane to our collection
        Document docAirplanes = DomUtil.buildDomDoc(xmlAirplanes);
        NodeList nodesAirplanes = docAirplanes.getElementsByTagName("Airplane");

        for (int i = 0; i < nodesAirplanes.getLength(); i++) {
            Element elementAirplane = (Element) nodesAirplanes.item(i);
            Airplane airplane = buildAirplane (elementAirplane);

            if (airplane.isValid()) {
                airplanes.add(airplane);
            }
        }

        return airplanes;
    }

    /**
     * Creates an Airplane object from a DOM node
     *
     * Processes a DOM Node that describes an Airplane and creates an Airplane object from the information
     * @param nodeAirplane is a DOM Node describing an Airplane
     * @return Airplane object created from the DOM Node representation of the Airplane
     *
     * @pre nodeAirplane is of format specified by CS509 server API
     */
    static private Airplane buildAirplane (Node nodeAirplane) {
        /**
         * Instantiate an empty Airplane object
         */
        Airplane airplane = new Airplane();

        String manufacture;
        String model;
        int firstClassSeat;
        int coachClassSeat;

        // The airplane element has attributes of Name and 3 character airplane code
        Element elementAirplane = (Element) nodeAirplane;
        manufacture = elementAirplane.getAttributeNode("Manufacturer").getValue();
        model = elementAirplane.getAttributeNode("Model").getValue();

        // The latitude and longitude are child elements
        Element elementAttr;
        elementAttr = (Element)elementAirplane.getElementsByTagName("FirstClassSeats").item(0);
        firstClassSeat = Integer.parseInt(DomUtil.getCharacterDataFromElement(elementAttr));

        elementAttr = (Element)elementAirplane.getElementsByTagName("CoachSeats").item(0);
        coachClassSeat= Integer.parseInt(DomUtil.getCharacterDataFromElement(elementAttr));

        /**
         * Update the Airplane object with values from XML node
         */
        airplane.setManufacture(manufacture);
        airplane.setModel(model);
        airplane.setFirstClassSeat(firstClassSeat);
        airplane.setCoachClassSeat(coachClassSeat);

        return airplane;
    }
}
