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

package de.ailis.scilter.filter;

import java.awt.image.BufferedImage;

import de.ailis.scilter.ScaleFilter;


/**
 * This is just a dummy because it simply does nothing. It returns the values
 * as they are, simulating a scale factor of x1.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class NormalFilter implements ScaleFilter
{
    /**
     * @see de.ailis.scilter.ScaleFilter#getScaleFactor()
     */

    @Override
    public int getScaleFactor()
    {
        return 1;
    }


    /**
     * @see de.ailis.scilter.ScaleFilter#scale(int[], int, int)
     */

    @Override
    public int[] scale(final int[] pixels, final int width, final int height)
    {
        return pixels;
    }


    /**
     * @see de.ailis.scilter.ScaleFilter#scale(java.awt.image.BufferedImage)
     */

    @Override
    public BufferedImage scale(final BufferedImage image)
    {
        return image;
    }


    /**
     * @see de.ailis.scilter.ScaleFilter#getImageType()
     */

    @Override
    public int getImageType()
    {
        return -1;
    }
}
