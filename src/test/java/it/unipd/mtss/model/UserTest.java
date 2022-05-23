////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import org.junit.Test;

import java.time.LocalDate;

public class UserTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullName() {
        new User(null,"cognome",
                "342214416", LocalDate.of(1990,1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullSurname() {
        new User("nome",null,
                "342214416", LocalDate.of(1990,1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithTelephoneNumber() {
        new User("nome","cognome",
                null, LocalDate.of(1990,1,1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithDateOfBirth() {
        new User("nome","cognome",
                "3251115356", null);
    }

    @Test
    public void testGetAgeReturnsTheCorrectAge() {
        User user  =new User("nome","cognome",
                "dawad", LocalDate.of(LocalDate.now().getYear()-18,1,1));
        int age = user.getAge();
        assert age == 18;
    }
}