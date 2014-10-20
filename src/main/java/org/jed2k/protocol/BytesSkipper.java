package org.jed2k.protocol;

import java.nio.ByteBuffer;
import org.jed2k.exception.JED2KException;

public class BytesSkipper implements Serializable {
    private final int skip_amount; 
    
    BytesSkipper(int skip_amount) {
        this.skip_amount = skip_amount;
    }
    
    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        src.position(skip_amount);
        return src;
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        assert(false);
        return dst;
    }

    @Override
    public int size() {
        return skip_amount;
    }
    
    
}