package org.dkf.jed2k.protocol.client;

import org.dkf.jed2k.Constants;
import org.dkf.jed2k.data.Range;
import org.dkf.jed2k.exception.JED2KException;
import org.dkf.jed2k.protocol.Hash;
import org.dkf.jed2k.protocol.Serializable;
import org.dkf.jed2k.protocol.UNumber;

import java.nio.ByteBuffer;

public abstract class RequestParts<N extends UNumber> implements Serializable {
    private int currentFreePos = 0;
    final Hash hash = new Hash();
    final UNumber [] beginOffset = new UNumber[Constants.PARTS_IN_REQUEST];
    final UNumber [] endOffset = new UNumber[Constants.PARTS_IN_REQUEST];

    public RequestParts() {
        initializeRanges();
    }

    public RequestParts(Hash h) {
        hash.assign(h);
        initializeRanges();
    }

    public void append(long b, long e) {
        assert(!isFool());
        beginOffset[currentFreePos].assign(b);
        endOffset[currentFreePos].assign(e);
        currentFreePos++;
    }

    public void append(Range r) {
        assert(!isFool());
        beginOffset[currentFreePos].assign(r.left);
        endOffset[currentFreePos].assign(r.right);
        currentFreePos++;
    }

    abstract void initializeRanges();

    public final boolean isFool() {
        return currentFreePos == Constants.PARTS_IN_REQUEST;
    }

    public final boolean isEmpty() {
        return currentFreePos == 0;
    }

    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        hash.get(src);
        for(Serializable s: beginOffset) s.get(src);
        for(Serializable s: endOffset) s.get(src);
        currentFreePos = Constants.PARTS_IN_REQUEST;
        return src;
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        hash.put(dst);
        for(Serializable s: beginOffset) s.put(dst);
        for(Serializable s: endOffset) s.put(dst);
        return dst;
    }

    public final Hash getHash() {
        return hash;
    }

    public final UNumber getBeginOffset(int i) {
        assert(i >= 0);
        assert(i < beginOffset.length);
        return beginOffset[i];
    }

    public final UNumber getEndOffset(int i) {
        assert(i >= 0);
        assert(i < endOffset.length);
        return endOffset[i];
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(hash.toString());
        for(int i = 0; i < Constants.PARTS_IN_REQUEST; ++i) {
            builder.append(String.format(" [%d..%d]", beginOffset[i].longValue(), endOffset[i].longValue()));
        }

        return builder.toString();
    }
}
