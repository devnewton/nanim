/*
 * Copyright (C) 2006-2012 Klaus Reimer <k@ailis.de>
 * 
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by the 
 * Free Software Foundation; either version 2.1 of the License, or (at your 
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.ailis.scilter;

import java.util.HashMap;
import java.util.Map;

import de.ailis.scilter.filter.Hq2xFilter;
import de.ailis.scilter.filter.Hq3xFilter;
import de.ailis.scilter.filter.Hq4xFilter;
import de.ailis.scilter.filter.Nearest2xFilter;
import de.ailis.scilter.filter.Nearest3xFilter;
import de.ailis.scilter.filter.Nearest4xFilter;
import de.ailis.scilter.filter.NormalFilter;
import de.ailis.scilter.filter.Scale2xFilter;
import de.ailis.scilter.filter.Scale3xFilter;


/**
 * The Scale Filter Factory can be queried about the implemented algorithms and
 * can return an implementation of a desired algorithm.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class ScaleFilterFactory
{
    /** The internal map with filter names and filter classes */
    private static final Map<String, Class<? extends ScaleFilter>> filters = buildFilterMap();


    /**
     * Private constructor
     */
    
    private ScaleFilterFactory()
    {
        super();
    }

    
    /**
     * Builds and returns the internal map with filter names and filter classes.
     * 
     * @return The internal filters map
     */

    private static final Map<String, Class<? extends ScaleFilter>> buildFilterMap()
    {
        Map<String, Class<? extends ScaleFilter>> filters;

        filters = new HashMap<String, Class<? extends ScaleFilter>>(3);
        filters.put("normal", NormalFilter.class);
        filters.put("nearest2x", Nearest2xFilter.class);
        filters.put("nearest3x", Nearest3xFilter.class);
        filters.put("nearest4x", Nearest4xFilter.class);
        filters.put("scale2x", Scale2xFilter.class);
        filters.put("scale3x", Scale3xFilter.class);
        filters.put("hq2x", Hq2xFilter.class);
        filters.put("hq3x", Hq3xFilter.class);
        filters.put("hq4x", Hq4xFilter.class);
        return filters;
    }


    /**
     * Returns an array with all the names of implemented scale filters. These
     * names can be used with the createFilter method to get an instance of a
     * scale filter implementation.
     * 
     * @return The array with implemented scale filter names
     */

    public static String[] getFilterNames()
    {
        return filters.keySet().toArray(new String[0]);
    }


    /**
     * Returns an instance of the Scale Filter with the specified name.
     * 
     * @param filterName
     *            The Scale Filter name
     * @return The instance of the Scale Filter
     */

    public static ScaleFilter createFilter(String filterName)
    {
        Class<? extends ScaleFilter> c;

        c = filters.get(filterName);
        if (c == null)
        {
            throw new IllegalArgumentException(
                "No implementation for scale filter with name " + filterName
                    + " was found");
        }
        try
        {
            return c.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(
                "Unable to create instance of scale filter: " + e, e);
        }
    }
}
