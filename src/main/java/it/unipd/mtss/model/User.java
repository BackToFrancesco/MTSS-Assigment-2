////////////////////////////////////////////////////////////////////
// [Lorenzo] [Onelia] [1226323]
// [Francesco] [Bacchin] [1227269]
////////////////////////////////////////////////////////////////////

package it.unipd.mtss.model;

import java.time.LocalDate;

public class User {
    private String name, surname, telephoneNumber;
    private LocalDate birthdate;

    public User(String name, String surname,
         String telephoneNumber, LocalDate birth) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birth;
        this.telephoneNumber = telephoneNumber;

        if(name == null) {
            throw new IllegalArgumentException(
                "Il nome non può essere nullo");
        }

        if(surname == null) {
            throw new IllegalArgumentException(
                "Il cognome non può essere nullo");
        }

        if(telephoneNumber == null) {
            throw new IllegalArgumentException(
                "Il numero di telefono non può essere nullo");
        }

        if(birth == null || birth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                "La data di nascita non può essere nulla");
        }
    }

    public int getAge() {
        return LocalDate.now().getYear()-birthdate.getYear();
    }
}