package se.sinch.binaryencodingmessage.service;

import se.sinch.binaryencodingmessage.exeption.InvalidMessageException;
import se.sinch.binaryencodingmessage.model.Message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * MessageCodecImpl has the business logic to generate the encoded data for the given message.
 *
 * @author Parasuram
 */

public class MessageCodecImpl implements MessageCodec {


    /**
     * Takes the Message as an Input and generates
     * the encoded form of given message as byte array.
     *
     * @param message, The message which needs to encode.
     * @return encoded message in byte[] format.
     */
    @Override
    public byte[] encode(Message message) throws InvalidMessageException {
        this.validateMessage(message);
        int totalSize = 4;
        for (Map.Entry<String, String> entry : message.headers.entrySet()) {
            totalSize += 4 + entry.getKey().length() + 4 + entry.getValue().length();
        }
        totalSize += 4 + message.payload.length;
        ByteBuffer buffer = ByteBuffer.allocate(totalSize);
        buffer.putInt(message.headers.size());

        for (Map.Entry<String, String> entry : message.headers.entrySet()) {
            buffer.putInt(entry.getKey().length());
            buffer.put(entry.getKey().getBytes());
            buffer.putInt(entry.getValue().length());
            buffer.put(entry.getValue().getBytes());
        }

        buffer.putInt(message.payload.length);
        buffer.put(message.payload);
        return buffer.array();
    }

    /**
     * Takes the encoded message as an Input and generates
     * the decoded data in the form of given Messsage object
     *
     * @param data, encoded data in byte[] format.
     * @return message object.
     */
    @Override
    public Message decode(byte[] data) {
        Message message = new Message();
        message.headers = new HashMap<>();

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
            message.headers.put(name, value);
        }

        int payloadLength = buffer.getInt();
        message.payload = new byte[payloadLength];
        buffer.get(message.payload);

        return message;
    }

    /**
     * Takes the incoming message as an Input and validates it.
     * Throws error in case of any validation errors.
     *
     * @param message .
     * @throws InvalidMessageException .
     */
    private void validateMessage(Message message) throws InvalidMessageException {
        if (null == message) {
            throw new InvalidMessageException("Invalid message");
        }
        if (message.headers.size() > 63) {
            throw new InvalidMessageException("Message headers maximum limit reached. Expected: 63, actual: " + message.headers.size());
        }
    }

}

