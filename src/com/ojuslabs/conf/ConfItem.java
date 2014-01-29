package com.ojuslabs.conf;

/**
 * Specifies how a configuration item should be parsed.
 */
public final class ConfItem
{
    // Name of the configuration item. This is always in a canonical format that
    // removes all spaces, and is in lower case.
    private String       _name;
    // Data type of the configuration item, in string format.
    private ConfDataType _dtype;
    // Default value of the configuration item; needs casting before use.
    private Object       _dvalue;

    /**
     * Constructs a new configuration item with the given specification.
     *
     * @param name
     *         Canonical text name of the configuration item.
     * @param dtype
     *         Data type of the item, expressed in string format.
     * @param dvalue
     *         Default value of the item.
     */
    public ConfItem(String name, ConfDataType dtype, Object dvalue) {
        if (null == name || null == dtype || name.equals("") || dtype.equals("")) {
            throw new IllegalArgumentException(
                    "All parameters have to be non-null and non-empty.");
        }

        _name = name;
        _dtype = dtype;
        _dvalue = dvalue;
    }

    /**
     * @return The name of the configuration item.
     */
    public String name() {
        return _name;
    }

    /**
     * @return The data type of the configuration item in string format.
     */
    public ConfDataType dataType() {
        return _dtype;
    }

    /**
     * @return The default value of the configuration item.
     */
    public Object defValue() {
        return _dvalue;
    }
}
