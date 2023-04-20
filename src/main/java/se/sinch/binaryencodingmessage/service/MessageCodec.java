package se.sinch.binaryencodingmessage.service;

import se.sinch.binaryencodingmessage.exeption.InvalidMessageException;
import se.sinch.binaryencodingmessage.exeption.ParsingException;
import se.sinch.binaryencodingmessage.model.Message;


public interface MessageCodec {
    byte[] encode(Message message) throws InvalidMessageException;
    Message decode(byte[] data) throws ParsingException;
}

