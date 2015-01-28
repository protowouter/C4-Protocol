/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Wouter Timmermans
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.woutertimmermans.connect4.protocol.base;

import nl.woutertimmermans.connect4.protocol.exceptions.SyntaxError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class AsyncC4Client {

    private static final Logger logger = LoggerFactory.getLogger(AsyncC4Client.class);

// ------------------ Instance variables ----------------

    private SelectionKey key;
    private List<CharBuffer> queue;

// --------------------- Constructors -------------------

    public AsyncC4Client(SelectionKey k) {

        key = k;
        queue = new LinkedList<CharBuffer>();

    }

    public synchronized void send(String command, C4Args args) throws SyntaxError {
        if (key != null) {
            StringBuilder sB = new StringBuilder();
            sB.append(command);
            String argString = args.serialize();
            logger.debug("Sending: {} {}", command, argString);
            if (!"".equals(args.serialize())) {
                sB.append(" ");
                sB.append(argString);
            }
            sB.append("\n");
            queue.add(CharBuffer.wrap(sB));


            key.interestOps(SelectionKey.OP_WRITE);
            key.selector().wakeup();
        }

    }

    public synchronized void write() {

        while (!queue.isEmpty()) {
            CharBuffer buf = queue.get(0);

            while (buf.hasRemaining()) {
                try {
                    ((SocketChannel) key.channel()).write(Charset.forName("UTF-8").encode(buf));
                } catch (IOException e) {
                }
            }
            queue.remove(0);
        }

        if (queue.isEmpty()) {
            key.interestOps(SelectionKey.OP_READ);
            key.selector().wakeup();
        }

    }

}
