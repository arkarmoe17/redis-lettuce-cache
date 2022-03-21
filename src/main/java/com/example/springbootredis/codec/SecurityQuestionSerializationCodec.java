package com.example.springbootredis.codec;

import com.example.springbootredis.model.entity.SecurityQuestion;
import io.lettuce.core.codec.RedisCodec;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SecurityQuestionSerializationCodec implements RedisCodec<String, SecurityQuestion> {

    private final Charset charSet = StandardCharsets.UTF_8;

    @Override
    public String decodeKey(ByteBuffer byteBuffer) {
        return charSet.decode(byteBuffer).toString();
    }

    @Override
    public SecurityQuestion decodeValue(ByteBuffer byteBuffer) {
        try {
            byte[] array = new byte[byteBuffer.remaining()];
            byteBuffer.get(array);
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(array));
            return (SecurityQuestion) is.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ByteBuffer encodeKey(String s) {
        return charSet.encode(s);
    }

    @Override
    public ByteBuffer encodeValue(SecurityQuestion sq) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(sq);
            return ByteBuffer.wrap(bytes.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
