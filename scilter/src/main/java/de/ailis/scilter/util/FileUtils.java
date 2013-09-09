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

import java.io.File;


/**
 * Statis utility methods for working with files
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class FileUtils
{
    /**
     * Private constructor
     */
    
    private FileUtils()
    {
        super();
    }
    
    
    /**
     * Returns the file extension of the specified file. If the file has no
     * extension then an empty string is returned.
     * 
     * @param file
     *            The file to get the extension from
     * @return The file extension or empty string if no extension was found
     */

    public static String getFileExtension(File file)
    {
        int pos;
        String filename;

        filename = file.getName();
        pos = filename.lastIndexOf('.');
        if (pos >= 0)
        {
            return filename.substring(pos + 1);
        }
        else
        {
            return "";
        }

    }


    /**
     * Returns the file extension of the specified filename. If the filename has
     * no extension then an empty string is returned.
     * 
     * @param filename
     *            The filename to get the extension from
     * @return The file extension or empty string if no extension was found
     */

    public static String getFileExtension(String filename)
    {
        return getFileExtension(new File(filename));
    }
}
