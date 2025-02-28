package com.brunozarth.syonet.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientConstructorAndGetters() {
        Client client = new Client(1L, "Keith Richards", "keith@example.com", LocalDate.of(1990,1,1));

        assertEquals(1L, client.getId());
        assertEquals("Keith Richards", client.getName());
        assertEquals("keith@example.com", client.getEmail());
        assertEquals(LocalDate.of(1990,1,1), client.getBirthdate());
    }

    @Test
    void testSetters() {
        Client client = new Client();
        client.setId(2L);
        client.setName("Keith Richards");
        client.setEmail("keith@example.com");
        client.setBirthdate(LocalDate.of(1990,1,1));

        assertEquals(2L, client.getId());
        assertEquals("Keith Richards", client.getName());
        assertEquals("keith@example.com", client.getEmail());
        assertEquals(LocalDate.of(1990,1,1), client.getBirthdate());
    }
}
