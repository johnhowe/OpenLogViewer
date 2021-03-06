/* Open Log Viewer
 *
 * Copyright 2011
 *
 * This file is part of the OpenLogViewer project.
 *
 * OpenLogViewer software is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenLogViewer software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with any OpenLogViewer software.  If not, see http://www.gnu.org/licenses/
 *
 * I ask that if you make any changes to this file you fork the code on github.com!
 *
 */

package org.diyefi.openlogviewer.decoder;

import java.io.File;
import java.io.IOException;
import org.diyefi.openlogviewer.genericlog.GenericLog;

/**
 *
 * @author Bryan
 */
public abstract class BaseDecoder implements Runnable {

    private File logFile;
    private GenericLog decodedLog;
    private Thread t;

    @Override
    public void run() {
        try {
            this.getDecodedLog().setLogStatus(GenericLog.LOG_LOADING);
            decodeLog();
            this.getDecodedLog().setLogStatus(GenericLog.LOG_LOADED);
        } catch (IOException IOE) {
            this.getDecodedLog().setLogStatus(GenericLog.LOG_NOT_LOADED);
            System.out.println("Error Loading Log: " +IOE.getMessage());
        }
    }

    abstract void decodeLog() throws IOException;

    public GenericLog getDecodedLog() {
        return decodedLog;
    }

    public void setDecodedLog(GenericLog decodedLog) {
        this.decodedLog = decodedLog;
    }

    public File getLogFile() {
        return logFile;
    }

    public void setLogFile(File logFile) {
        this.logFile = logFile;
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }



}
