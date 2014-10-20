package org.jed2k.protocol;

import static org.jed2k.Utils.sizeof;
import static org.jed2k.Utils.int2Address;
import org.jed2k.exception.JED2KException;

import java.nio.ByteBuffer;

public final class NetworkIdentifier implements Serializable {
    public int ip = 0;
    public short port = 0;

    public NetworkIdentifier() {
    }
    
    public NetworkIdentifier(int ip, short port) {
        this.ip = ip;
        this.port = port;
    }
    
    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        ip = src.getInt();
        port = src.getShort();
        return src;
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        return dst.putInt(ip).putShort(port);
    }
    
    @Override
    public int size() {
        return sizeof(ip) + sizeof(port);
    }
    
    @Override
    public String toString() {
        return int2Address(ip) + ":" + port; 
    }
}