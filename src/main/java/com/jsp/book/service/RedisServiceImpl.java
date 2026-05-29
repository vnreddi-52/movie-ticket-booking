package com.jsp.book.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.jsp.book.dto.UserDto;
import com.jsp.book.entity.BookedTicket;

@Service
public class RedisServiceImpl implements RedisService {

    // In-memory stores (replaces Redis)
    private final Map<String, UserDto> userDtoStore = new ConcurrentHashMap<>();
    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final Map<String, BookedTicket> ticketStore = new ConcurrentHashMap<>();

    @Override
    public void saveUserDto(String email, UserDto userDto) {
        userDtoStore.put(email, userDto);
        System.out.println("✅ UserDto saved in memory for: " + email);
    }

    @Override
    public void saveOtp(String email, int otp) {
        otpStore.put(email, otp);
        System.out.println("✅ OTP for " + email + " is: " + otp);
    }

    @Override
    public UserDto getUserDto(String email) {
        UserDto userDto = userDtoStore.get(email);
        if (userDto == null) {
            System.out.println("⚠️ No UserDto found for: " + email);
        }
        return userDto;
    }

    @Override
    public int getOtp(String email) {
        return otpStore.getOrDefault(email, 0);
    }

    @Override
    public void saveTicket(String orderId, BookedTicket ticket) {
        ticketStore.put(orderId, ticket);
        System.out.println("✅ Ticket saved in memory for orderId: " + orderId);
    }

    @Override
    public BookedTicket getTicket(String orderId) {
        BookedTicket ticket = ticketStore.get(orderId);
        if (ticket == null) {
            System.out.println("⚠️ No ticket found for orderId: " + orderId);
        }
        return ticket;
    }
}