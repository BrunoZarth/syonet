package com.brunozarth.syonet.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testClientConstructorAndGetters() {
        Client client = new Client(1L, "Keith Richards", "keith@example.com", "1990-05-15");

        assertEquals(1L, client.getId());
        assertEquals("Keith Richards", client.getName());
        assertEquals("keith@example.com", client.getEmail());
        assertEquals("1990-05-15", client.getBirth());
    }

    @Test
    void testSetters() {
        Client client = new Client();
        client.setId(2L);
        client.setName("Keith Richards");
        client.setEmail("keith@example.com");
        client.setBirth("1992-10-20");

        assertEquals(2L, client.getId());
        assertEquals("Keith Richards", client.getName());
        assertEquals("keith@example.com", client.getEmail());
        assertEquals("1992-10-20", client.getBirth());
    }
}
