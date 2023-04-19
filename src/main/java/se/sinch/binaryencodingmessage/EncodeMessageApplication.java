package se.sinch.binaryencodingmessage;

import java.util.HashMap;
import java.util.Map;

import se.sinch.binaryencodingmessage.model.Message;
import se.sinch.binaryencodingmessage.service.MessageCodecImpl;

/**
 * Message encoding application designed to encode the given input message along with header
 * and decoded the encoded information to normal string.
 *
 * @author Parasuram
 */
public class EncodeMessageApplication
{
    public static void main (String[] args) throws java.lang.Exception
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("key1", "message1");
        headers.put("key2", "message2");

        byte[] bytes = "message bytes".getBytes();

        Message message = new Message(headers, bytes);

        System.out.println("Original message :  " + message);

        MessageCodecImpl codec = new MessageCodecImpl();
        byte[] encoded = codec.encode(message);

        System.out.println("Encoded Message : " + new String(encoded));

        Message decoded = codec.decode(encoded);

        System.out.println("Decoded Message : " + decoded);

    }
}
