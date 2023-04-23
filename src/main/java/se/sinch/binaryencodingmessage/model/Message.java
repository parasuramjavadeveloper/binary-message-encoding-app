package se.sinch.binaryencodingmessage.model;

import lombok.*;

import java.util.Map;

/*
  Model class for Message data
 * @author Parasuram
 */
@Value
@AllArgsConstructor
public class Message {

        @NonNull
        Map<String, String> headers;

        @NonNull
        byte[] payload;

        @Override
        public String toString(){
            return "[headers=" + headers +", payload=" + new String(payload) + "]";
        }
}
