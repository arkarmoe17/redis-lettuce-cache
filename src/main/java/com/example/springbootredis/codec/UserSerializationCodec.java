package com.example.springbootredis.codec;

import com.example.springbootredis.model.entity.User;
import io.lettuce.core.codec.RedisCodec;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UserSerializationCodec implements RedisCodec<String, User> {

    private final Charset charSet = StandardCharsets.UTF_8;

    @Override
    public String decodeKey(ByteBuffer byteBuffer) {
        return charSet.decode(byteBuffer).toString();
    }

    @Override
    public User decodeValue(ByteBuffer byteBuffer) {
        try {
            byte[] array = new byte[byteBuffer.remaining()];
            byteBuffer.get(array);
            ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(array));
            return (User) is.readObject();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ByteBuffer encodeKey(String s) {
        return charSet.encode(s);
    }

    @Override
    public ByteBuffer encodeValue(User user) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bytes);
            os.writeObject(user);
            return ByteBuffer.wrap(bytes.toByteArray());
        } catch (IOException e) {
            return null;
        }
    }
}
