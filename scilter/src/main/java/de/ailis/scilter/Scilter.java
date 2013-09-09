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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.ailis.scilter.util.FileUtils;


/**
 * A simple command line program to scale a picture with one of the scaling
 * filters of Scilter.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Scilter
{
    /**
     * Private constructor
     */

    private Scilter()
    {
        super();
    }


    /**
     * First parameter is the scaling method, second parameter is the source
     * file and the third parameter is the destination file.
     *
     * @param args
     *            The command line arguments
     * @throws IOException
     *             When file operation fails.
     */

    public static void main(final String[] args) throws IOException
    {
        BufferedImage in, out;
        File file;

        if (args.length != 3)
        {
            System.out.println("Syntax: Scilter ALGORITHM INFILE OUTFILE");
            System.out.println("Implemented algorithms:");
            for (final String name: ScaleFilterFactory.getFilterNames())
            {
                System.out.print("  ");
                System.out.println(name);
            }
            System.exit(0);
        }

        in = ImageIO.read(new File(args[1]));
        out = ScaleFilterFactory.createFilter(args[0]).scale(in);
        file = new File(args[2]);
        ImageIO.write(out, FileUtils.getFileExtension(file), new File(args[2]));
    }
}
