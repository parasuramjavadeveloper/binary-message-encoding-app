package se.sinch.binaryencodingmessage.service;

import org.junit.jupiter.api.Test;
import se.sinch.binaryencodingmessage.exeption.InvalidMessageException;
import se.sinch.binaryencodingmessage.model.Message;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MessageCodecServiceImpl class.
 * @author Parasuram
 */
public class MessageCodecTest {

    MessageCodecImpl service = new MessageCodecImpl();

    /*
    Tests the success case scenario of encode and decode features
     */
    @Test
    public void testMessageEncodeSuccess() throws InvalidMessageException {
        byte[] encodedMessage =  service.encode(getNormalMessage());
        assertNotNull(encodedMessage);
        Message decodedMessage = service.decode(encodedMessage);
        assertEquals(2,decodedMessage.headers.size());
    }

    /*
    Tests the exception scenario when null value passed as message.
     */
    @Test
    public void testMessageEncodingFailure()
    {
        assertThrows(InvalidMessageException.class, () -> {
            service.encode(null);
        });
    }

    /*
    Tests the scenario when message encoding got succes
    and decoding was failed due to errors.
     */
    @Test
    public void testEncodeSuccessDecodeError() throws InvalidMessageException {
        byte[] encodedMessage =  service.encode(getNormalMessage());
        encodedMessage[60] = 101;
        Message decodedMessage = service.decode(encodedMessage);
        assertNotEquals("message bytes",decodedMessage.payload);
    }

    /*
    Tests the scenario, when malformed string send to decode.
     */
    @Test
    public void testMalformedEncodedString() throws InvalidMessageException {
        byte[] encodedMessage =  service.encode(getNormalMessage());
        encodedMessage[0] = 101;
        assertThrows(Exception.class,() ->{
            service.decode(encodedMessage);
        });
    }

    /*
    Tests the exception scenario when number of headers exceeds the maximum limit.
     */
    @Test
    public void invalidHeadersSize() throws InvalidMessageException {
        Map<String, String> headers = new HashMap<>();
        for(int i=0;i<65;i++){
            headers.put("key"+i, "message"+i);
        }
        byte[] bytes = "message bytes".getBytes();
        Message message = new Message(headers, bytes);

        assertThrows(InvalidMessageException.class,() ->{
            service.encode(message);
        });
    }

    private Message getNormalMessage()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("key1", "message1");
        headers.put("key2", "message2");

        byte[] bytes = "message bytes".getBytes();

        return new Message(headers, bytes);
    }
}
