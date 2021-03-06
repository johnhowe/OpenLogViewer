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
import java.io.FileInputStream;
import java.io.IOException;
import org.diyefi.openlogviewer.genericlog.GenericLog;

/**
 *
 * @author Bryan
 */
public class FreeEMSByteLA implements Runnable { // implements runnable to make this class theadable

    private final short ESCAPE_BYTE = 0xBB;// for unsigned byte
    private final short START_BYTE = 0xAA;// for unsigned byte
    private final short STOP_BYTE = 0xCC;// for unsigned byte
    private final short ESCAPED_ESCAPE_BYTE = 0x44;// for unsigned byte
    private final short ESCAPED_START_BYTE = 0x55;// for unsigned byte
    private final short ESCAPED_STOP_BYTE = 0x33;// for unsigned byte
    private final int CHECKSUM_VAL = 256;
    private short[] wholePacket;// for unsigned byte
    private File logFile;
    private FileInputStream logStream;
    private boolean startFound;
    private GenericLog decodedLog;
    private Thread t;

    String[] headers = {
            "Line", "PTIT", "T0", "T1", "T2", "T3", "T4", "T5", "T6", "T7"
        }; //This needs to be converted to resourses or gathered externally at some point
        private double[] conversionFactor = { // no value in this shall == 0, you cannot divide by 0 ( divide by 1 if you need raw value )
            1.0, // PTIT
            1.0, // T0
            1.0, // T1
            1.0, // T2
            1.0, // T3
            1.0, // T4
            1.0, // T5
            1.0, // T6
            1.0  // T7
        };

    // NO default constructor, a file or path to a file MUST be given
    // Reason: File()'s constructors are ambiguous cannot give a null value
    /**
     * FreeEmsBin Constructor: <code>String</code> path to your binary log
     * @param String path
     *
     */
    public FreeEMSByteLA(String path) {

        this(new File(path));


    }

    /**
     * FreeEmsBin Constructor: <code>File</code> object of your Binary log
     * @param File f
     */
    public FreeEMSByteLA(File f) {
        logFile = f;
        startFound = false;
        wholePacket = new short[3000];
        decodedLog = new GenericLog(headers);

        t = new Thread(this, "FreeEMSByteLA Loading");
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();

    }

    

    /**
     * DecodeLog will use the current <code>logFile</code> parse through it and when required pass the job <br>
     * to the required method of this class such as decodePacket or checksum.
     */
    //public void decodeLog() {
    public void run() {
        try {
            // file setup
            byte[] readByte = new byte[1];
            short uByte = 0;// uByte stands for UNSIGNED BYTE

            startFound = false;
            logStream = new FileInputStream(logFile);
            decodedLog.setLogStatus(0);
            int packetLength = 0;
            int packetCount = 0;
            while (logStream.read(readByte) != -1) {
                uByte = (short) (readByte[0] & 0xff); // mask the byte in case something screwey happens
                if (uByte == START_BYTE) {
                    if (!startFound) {
                        startFound = true;
                    } else {
                        //TO-DO: find something to put here
                    }
                } else if (startFound) { // do NOTHING untill a start is found

                    if (uByte == STOP_BYTE) {
                        if (checksum(wholePacket, packetLength)) {
                        	packetCount++;
                            decodePacket(wholePacket, packetLength, packetCount);
                            startFound = false;
                        }
                        packetLength = 0;
                    } else if (uByte == ESCAPE_BYTE) {
                        if (logStream.read(readByte) != -1) { // read in the next byte, as it is to be escaped
                            uByte = unEscapeByte((short) (readByte[0] & 0xff)); // unescape this byte
                            if (uByte != (short) -1) {
                                wholePacket[packetLength] = uByte; // add the escaped byte to a temp storage area for processing later
                                packetLength++;

                            } else {
                                startFound = false; // Data was bad, the rest of the data should be ignored
                            }
                        }
                    } else {
                        wholePacket[packetLength] = uByte; // add the info to a temp storage area for processing later
                        packetLength++;
                    }
                }
                //else-> No else because if the start byte or start found conditions
                // were not met then ignore data untill start is found due to a packet being bad
            }
            decodedLog.setLogStatus(1);
            

        } catch (IOException IOE) {
            System.out.println(IOE.getMessage());
            //TO-DO: Add code to handle or warn of the error
            
        } 

    }

    /**
     * This method decodes a packet by splitting up the data into larger datatypes to keep the unsigned info <br>
     * This method could probably use a litle work
     * @param packet is a <code>short</code> array containing 1 full packet
     *
     */
    private void decodePacket(short[] packet, int length, int count) {
    	int flags = (int)packet[0];
        int payLoadId = (int) (((packet[1] & 0xff) * 256) + (packet[2] & 0xff));

        int leadingBytes = 0;
        // set size according to payload
        if (payLoadId == 407) {
            leadingBytes = 3;
        }
        
        //d = ((short) (packet[x + leadingBytes] * 256) + packet[x + leadingBytes + 1]) / conversionFactor[x / 2];// special case signed short
        //d = (int) ((packet[x + leadingBytes] * 256) + packet[x + leadingBytes + 1]) / conversionFactor[x / 2];// unsigned shorts
        int PTIT = (int)packet[3];
        int T0 = 0;
        int T1 = 0;
        int T2 = 0;
        int T3 = 0;
        int T4 = 0;
        int T5 = 0;
        int T6 = 0;
        int T7 = 0;
        decodedLog.addValue("PTIT", PTIT);
        
        if((PTIT % 2) == 1){
        	T0 = 1;
        	PTIT -= 1;
        }
        if((PTIT % 4) == 2){
        	T1 = 1;
        	PTIT -= 2;
        }
        if((PTIT % 8) == 4){
        	T2 = 1;
        	PTIT -= 4;
        }
        if((PTIT % 16) == 8){
        	T3 = 1;
        	PTIT -= 8;
        }
        if((PTIT % 32) == 16){
        	T4 = 1;
        	PTIT -= 16;
        }
        if((PTIT % 64) == 32){
        	T5 = 1;
        	PTIT -= 32;
        }
        if((PTIT % 128) == 64){
        	T6 = 1;
        	PTIT -= 64;
        }
        if(PTIT == 128){
        	T7 = 1;
        }

        decodedLog.addValue("Line", count);
        decodedLog.addValue("T0", T0);
        decodedLog.addValue("T1", T1);
        decodedLog.addValue("T2", T2);
        decodedLog.addValue("T3", T3);
        decodedLog.addValue("T4", T4);
        decodedLog.addValue("T5", T5);
        decodedLog.addValue("T6", T6);
        decodedLog.addValue("T7", T7);
    }

    /**
     * performs a check sum based on the packet data <br>
     * the checksum needs to be improved however
     * @param packet
     * @return true or false based on if the checksum passes
     */
    private boolean checksum(short[] packet, int length) {
    	if(length > 0){
    		short includedSum = packet[length -1]; // sum is last byte
    		long veryBIGsum = 0;
    		for(int x=0;x<length-1;x++){
    			veryBIGsum += packet[x];
    		}
    		short calculatedSum = (short)(veryBIGsum % 256);
    		return (calculatedSum == includedSum) ? true : false;
    	}else{
    		return false;
    	}
    }

    /**
     * takes the byte to be escaped and returns the proper value
     * @param uByte - byte to be Un-escaped
     * @return -1 if bad data or the proper value of the escaped byte
     */
    private short unEscapeByte(short uByte) {
        if (uByte == ESCAPED_START_BYTE) {
            return START_BYTE;
        } else if (uByte == ESCAPED_STOP_BYTE) {
            return STOP_BYTE;
        } else if (uByte == ESCAPED_ESCAPE_BYTE) {
            return ESCAPE_BYTE;
        } else {
            return (short) -1;
        }
    }

    /**
     *
     * @return getGenericLog() returns the reference to the generic log the binary data has been converted to
     */
    /*public GenericLog getGenericLog() {
        if (logLoaded) {
            return decodedLog;
        } else {
            return null;
        }
    }*/



    /**
     *
     * @return Misc data about this log
     * <br> to be implemented in full later
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
