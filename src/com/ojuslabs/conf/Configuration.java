package com.ojuslabs.conf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.ojuslabs.util.StringUtils;

/**
 * Holds the user-specified configuration from parsed files.
 */
public final class Configuration
{
    private final Map<String, ConfItem> _confItems;
    private final String _fileName;
    private final Map<String, Map<String, Object>> _conf;

    /**
     * @param fileName
     *         The configuration type for which this is being instantiated.
     * @throws IOException
     */
    public Configuration(Map<String, ConfItem> confItems,
                         String fileName) throws IOException {
        if (null == fileName || fileName.equals("")) {
            throw new IllegalArgumentException("Provide a non-empty configuration type.");
        }

        _confItems = confItems;
        _fileName = fileName;
        _conf = Maps.newHashMap();

        parseConf();
    }

    /**
     * Parses the given configuration file, and sets the obtained values in the maps.
     *
     * @throws IOException
     */
    void parseConf() throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(_fileName));

        String section = null;
        Map<String, Object> m = null;

        try {
            String line;
            while (null != (line = r.readLine())) {
                line = line.trim();

                // A blank line or a comment line; ignore.
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // New section header. We create a new map for the new section.
                if (line.startsWith("[")) {
                    int pos = line.indexOf(']');
                    section = StringUtils.squeezeFully(line.substring(1, pos), " -")
                                         .toLowerCase();
                    m = Maps.newHashMap();
                    _conf.put(section, m);
                    // System.err.printf("-- Parsing section: `%s'.\n",
                    // section);
                    continue;
                }

                // If we have come here, we must have a valid section header.
                if (null == section) {
                    throw new IllegalStateException(
                            "Encountered the first configuration item before any section header.");
                }

                String[] kv = line.split("=");
                String k = StringUtils.squeezeFully(kv[0], " -").toLowerCase();
                String v = kv[1].trim().toLowerCase();
                // System.err.printf("-- Parsing key: `%s', value: `%s'.\n", k,
                // v);

                ConfItem ci = _confItems.get(k);
                // If unknown parameter, we ignore it.
                if (null == ci) {
                    continue;
                }

                // We convert input values to their respective data types.
                switch (ci.dataType()) {
                    case BOOL:
                        m.put(k, Boolean.valueOf(v));
                        break;
                    case INT:
                        m.put(k, Integer.valueOf(v));
                        break;
                    case DOUBLE:
                        m.put(k, Double.valueOf(v));
                        break;
                    case STRING:
                        m.put(k, v);
                        break;
                    case STRING_LIST:
                        int pos1 = v.indexOf('(');
                        int pos2 = v.indexOf(')');
                        String[] vals = v.substring(pos1 + 1, pos2).split(",");
                        if (vals.length > 0) {
                            for (int i = 0; i < vals.length; i++) {
                                vals[i] = vals[i].trim();
                            }
                            m.put(k, vals);
                        }
                        break;
                    default:
                        m.put(k, v);
                }
            }
        } finally {
            if (null != r) {
                r.close();
            }
        }
    }

    /**
     * Answers the configuration section corresponding to the given section name.
     *
     * @param sec
     *         Name of the section desired.
     * @return The corresponding section in this configuration, if found; {@code null}
     * otherwise.
     */
    public Map<String, Object> section(String sec) {
        return _conf.get(sec);
    }

    /**
     * Answers the given configuration item within the given section.
     *
     * @param sec
     *         Name of the section desired.
     * @param key
     *         Name of the configuration parameter whose value is desired.
     * @return Value of the given parameter in the given section, if found; {@code null}
     * otherwise.
     */
    public Object value(String sec, String key) {
        Map<String, Object> m = _conf.get(sec);
        if (null == m) {
            return null;
        }

        return m.get(key);
    }

    /**
     * Answers the given configuration item within the given section if found. Otherwise,
     * it answers the default value pre-configured for the parameter.
     *
     * @param sec
     *         Name of the section desired.
     * @param key
     *         Name of the configuration parameter whose value is desired.
     * @return Value of the given parameter in the given section, if found; else, the
     * default value of the parameter, if found; {@code null} otherwise.
     */
    public Object valueOrDefault(String sec, String key) {
        Map<String, Object> m = _conf.get(sec);
        if (null == m) {
            return null;
        }
        Object o = m.get(key);
        if (null != o) {
            return o;
        }

        ConfItem ci = _confItems.get(key);
        if (null == ci) {
            return null;
        }
        return ci.defValue();
    }
}
