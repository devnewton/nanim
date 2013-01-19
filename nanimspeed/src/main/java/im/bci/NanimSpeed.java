/*
 * Copyright (c) 2013 devnewton <devnewton@bci.im>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'devnewton <devnewton@bci.im>' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package im.bci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Nanim;
import im.bci.nanim.NanimParser;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class NanimSpeed {

    private CommandLine commandLine;

    public NanimSpeed(CommandLine line) {
        this.commandLine = line;
    }

    public static void main(String[] args) throws ParseException, IOException {
        Options options = new Options();
        options.addOption("a", true, "adjust this animation speed instead of all");
        options.addOption("r", true, "speed rate to apply");

        if (args.length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("nanimrename [args] foo.nanim foo2.nanim foo3.nanim ...", options);
            return;
        }

        GnuParser parser = new GnuParser();
        CommandLine line = parser.parse(options, args);

        if (!line.hasOption("r") || line.getArgList().size() < 1) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("nanimrename [args] foo.nanim foo2.nanim foo3.nanim ...", options);
            return;
        }

        NanimSpeed nanimSpeed = new NanimSpeed(line);
        for (int i = 0; i < line.getArgList().size(); ++i) {
            String filename = line.getArgList().get(i).toString();
            Nanim nanim = nanimSpeed.decode(filename);
            nanim = nanimSpeed.adjustSpeed(nanim);
            nanimSpeed.save(filename, nanim);
        }
    }

    private Nanim decode(String filename) throws IOException {
        File inputFile = new File(filename);
        FileInputStream is = new FileInputStream(inputFile);
        try {
            return NanimParser.Nanim.parseFrom(is);
        } finally {
            is.close();
        }
    }

    private Nanim adjustSpeed(Nanim oldNanim) {
        Nanim.Builder newNanim = Nanim.newBuilder(oldNanim);

        String animationName = null;
        if (commandLine.hasOption("a")) {
            animationName = commandLine.getOptionValue("a");
        }
        float speedRate = Float.parseFloat(commandLine.getOptionValue("r"));

        for (int a = 0; a < newNanim.getAnimationsCount(); ++a) {
            Animation animation = newNanim.getAnimations(a);
            if (null == animationName || animationName.equals(animation.getName())) {
                Animation.Builder newAnimation = Animation.newBuilder(animation);
                for (int f = 0; f < newAnimation.getFramesCount(); ++f) {
                    Frame oldFrame = newAnimation.getFrames(f);
                    int newDuration = (int) (oldFrame.getDuration() * speedRate);
                    newAnimation.setFrames(f, Frame.newBuilder(oldFrame).setDuration(newDuration));
                }
                 newNanim.setAnimations(a, newAnimation);
            }           
        }
        return newNanim.build();
    }

    private void save(String filename, Nanim nanim) throws IOException {
        FileOutputStream os = new FileOutputStream(filename);
        try {
            nanim.writeTo(os);
            System.out.println("nanim successfully written to " + filename);
        } finally {
            os.flush();
            os.close();
        }
    }
}
