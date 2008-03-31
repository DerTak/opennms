package org.opennms.web.map;



/**
 * Signals that an attempt to obtain the map denoted by a specified identifier has failed.
 */
public class MapsManagementException extends MapsException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new MapNotFoundException with no detail message.
     */
    public MapsManagementException() {
        super();
    }

    /**
     * Create a new MapNotFoundException with the String specified as an error message.
     * @param msg   The error message for the exception.
     */
    public MapsManagementException(String msg) {
        super(msg);
    }

}
