package se.sinch.binaryencodingmessage.service;

import lombok.NonNull;
import se.sinch.binaryencodingmessage.exeption.InvalidMessageException;
import se.sinch.binaryencodingmessage.exeption.ParsingException;
import se.sinch.binaryencodingmessage.model.Message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * MessageCodecImpl has the business logic to generate the encoded data for the given message.
 * @author Parasuram
 */

public class MessageCodecImpl implements MessageCodec {


    /**
     * Takes the Message as an Input and generates the encoded form of given message as byte array.
     * The Message object contains headers and payLoad, the maximum headers allowed are 63.
     * The encoded message size will dynamically change based on the total size of headers and payload in the message.
     * @param message, The message which needs to encode.
     * @return encoded message in byte[] format.
     * @throws InvalidMessageException if input was invalid.
     * In case of null input value, the method will throw NullPointerException.
     * Explanation :- Initially Taken totalSize as 4 bytes of memory allocation and then iterating the total headers adding each header key length and value Length
     * to the totalSize and finally adding payload length to this totalSize of byteArray.
     * Added Message Headers size to the bytebuffer ,added each header key length and its bytes
     * ,added each header value length and its bytes and finally added Message Payload Length and Message Payload.
     */
    @Override
    public byte[] encode(@NonNull Message message) throws InvalidMessageException {
        this.validateMessage(message);
        int totalSize = 4;
        for (Map.Entry<String, String> entry : message.getHeaders().entrySet()) {
            totalSize += 4 + entry.getKey().length() + 4 + entry.getValue().length();
        }
        totalSize += 4 + message.getPayload().length;
        ByteBuffer buffer = ByteBuffer.allocate(totalSize);
        buffer.putInt(message.getHeaders().size());

        for (Map.Entry<String, String> entry : message.getHeaders().entrySet()) {
            buffer.putInt(entry.getKey().length());
            buffer.put(entry.getKey().getBytes());
            buffer.putInt(entry.getValue().length());
            buffer.put(entry.getValue().getBytes());
        }

        buffer.putInt(message.getPayload().length);
        buffer.put(message.getPayload());
        return buffer.array();
    }

    /**
     * Takes the encoded message as an Input and generates the decoded data in the form of given Message object
     * @param data, encoded data in byte[] format.
     * @return message object.
     * @throws ParsingException, if the encoded message was malformed or if user sent invalid input to decode.
     *  Explanation :- The way we are added in the encode message of its bytes in the same way need to take back here in the decoded message.
     *  So the first int is number Of Headers,next is Header each key size and each key bytes and Header each value length and each value bytes
     *  and finally payload length and payload bytes.
     */
    @Override
    public Message decode(byte[] data) throws ParsingException {
        Map<String,String> headers = new HashMap<String,String>();
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);

            int numHeaders = buffer.getInt();

            for (int i = 0; i < numHeaders; i++) {
                int nameLength = buffer.getInt();
                byte[] nameBytes = new byte[nameLength];
                buffer.get(nameBytes);
                String name = new String(nameBytes);
                int valueLength = buffer.getInt();
                byte[] valueBytes = new byte[valueLength];
                buffer.get(valueBytes);
                String value = new String(valueBytes);
                headers.put(name, value);
            }
            int payloadLength = buffer.getInt();
            byte[] payload = new byte[payloadLength];
            buffer.get(payload);
            return new Message(headers,payload);
        }
        catch(Exception exception)
        {
           throw new ParsingException(exception.getMessage());
        }
   }

    /**
     * Takes the incoming message as an Input and validates it.
     * Throws error in case of any validation errors.
     * @param message .
     * @throws InvalidMessageException .
     */
    private void validateMessage(Message message) throws InvalidMessageException {
        if(message.getHeaders().size()>63){
            throw new InvalidMessageException("Message headers maximum limit reached. Expected: 63, actual: " +message.getHeaders().size());
        }
    }

}

