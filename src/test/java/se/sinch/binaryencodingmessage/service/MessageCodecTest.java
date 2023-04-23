package se.sinch.binaryencodingmessage.service;

import org.junit.jupiter.api.Test;
import se.sinch.binaryencodingmessage.exeption.InvalidMessageException;
import se.sinch.binaryencodingmessage.exeption.ParsingException;
import se.sinch.binaryencodingmessage.model.Message;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MessageCodecImpl class.
 * @author Parasuram
 */
public class MessageCodecTest {

    MessageCodecImpl messageCodec = new MessageCodecImpl();

    /*
    Tests the success case scenario of encode and decode features
     */
    @Test
    public void testMessageEncodeSuccess() throws ParsingException, InvalidMessageException {
        byte[] encodedMessage =  messageCodec.encode(getNormalMessage());
        assertNotNull(encodedMessage);
        Message decodedMessage = messageCodec.decode(encodedMessage);
        assertEquals(5,decodedMessage.getHeaders().size());
    }

    /*
    Tests the exception scenario when null value passed as message.
     */
    @Test
    public void testMessageEncodingFailure()
    {
        assertThrows(NullPointerException.class, () -> {
            messageCodec.encode(null);
        });
    }

    /*
   Tests the exception scenario when null value passed in headers.
    */
    @Test
    public void testMessageEncodingWithNullHeaders() {
        assertThrows(NullPointerException.class, () -> {
            Message message = new Message(null,"sample payload".getBytes());
            messageCodec.encode(message);
        });
    }

    /*
    Tests the exception scenario duplicate headers.
    */
    @Test
    public void testMessageEncodingWithDuplicateHeaders() throws InvalidMessageException, ParsingException {
        HashMap<String,String > headers = new HashMap<>();
        headers.put("Authorization","noAuth");
        headers.put("duplicateKey","duplicateValue");
        headers.put("duplicateKey","duplicateValue");
        Message message = new Message(headers,"Sample payload.".getBytes());
        byte[] encodedDate = messageCodec.encode(message);
        Message decodedMessage = messageCodec.decode(encodedDate);
        assertEquals(2,decodedMessage.getHeaders().size());
    }

    /*
    Tests the scenario when message encoding got success
    and decoding was failed due to errors.
     */
    @Test
    public void testEncodeSuccessDecodeError() throws InvalidMessageException, ParsingException {
        byte[] encodedMessage =  messageCodec.encode(getNormalMessage());
        encodedMessage[60] = 101;
        assertThrows(ParsingException.class,() ->{
            messageCodec.decode(encodedMessage);
        });
    }

    /*
    Tests the scenario, when malformed string sent to decode method.
     */
    @Test
    public void testMalformedEncodedString() throws InvalidMessageException {
        byte[] encodedMessage =  messageCodec.encode(getNormalMessage());
        encodedMessage[0] = 101;
        assertThrows(ParsingException.class,() ->{
            messageCodec.decode(encodedMessage);
        });
    }

    /*
    Tests the exception scenario when number of headers exceeds the maximum limit.
     */
    @Test
    public void testWhenInValidHeadersSizeGiven() {
        Map<String, String> headers = new HashMap<>();
        for(int i=0;i<65;i++){
            headers.put("key"+i, "message"+i);
        }
        byte[] bytes = "message bytes".getBytes();
        Message message = new Message(headers, bytes);

        assertThrows(InvalidMessageException.class,() ->{
            messageCodec.encode(message);
        });
    }

    private Message getNormalMessage()
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("key1", "message1");
        headers.put("key2", "message2");
        headers.put("key3", "message3");
        headers.put("key4","message4");
        headers.put("authorization","bearer_token");

        byte[] bytes = "message bytes".getBytes();

        return new Message(headers, bytes);
    }
}
