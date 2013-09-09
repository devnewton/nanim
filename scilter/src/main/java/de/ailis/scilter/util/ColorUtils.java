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

package de.ailis.scilter.util;


/**
 * Statis utility methods for working with colors
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class ColorUtils
{
    /**
     * Private constructor
     */

    private ColorUtils()
    {
        super();
    }


    /**
     * Converts an RGB 888 color to RGB 565 color space. Alpha values will get
     * lost.
     * 
     * @param rgb888
     *            The RGB 888 color value
     * @return The RGB 565 color
     */

    public static int convertRgb888To565(int rgb888)
    {
        return ((rgb888 & 0xf80000) >> 8) | ((rgb888 & 0xfc00) >> 5)
            | ((rgb888 & 0xf8) >> 3);
    }


    /**
     * Converts an RGB 888 color array to an RGB 565 color array. Alpha values
     * will get lost.
     * 
     * @param rgb888
     *            The RGB 888 color array
     * @return The RGB 565 color array
     */

    public static int[] convertRgb888To565(int[] rgb888)
    {
        int max;
        int[] rgb565;

        max = rgb888.length;
        rgb565 = new int[max];
        for (int i = 0; i < max; i++)
        {
            rgb565[i] = convertRgb888To565(rgb888[i]);
        }
        return rgb565;
    }
}
