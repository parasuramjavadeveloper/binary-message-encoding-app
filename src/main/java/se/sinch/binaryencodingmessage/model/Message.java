package se.sinch.binaryencodingmessage.model;

import java.util.Map;

/*
  Model class for Message data
 * @author Parasuram
 */
public class Message {
        public Map<String, String> headers;
        public byte[] payload;

        public Message() {}
        public Message(Map<String, String> headers, byte[] payload) {
            this.headers = headers;
            this.payload = payload;
        }

        @Override
        public String toString(){
            return "[headers=" + headers +", payload=" + new String(payload) + "]";
        }
}
