package ro.service.management.system.angajat;

import ro.service.management.system.exceptii.InvalidUserInputException;

import java.time.LocalDate;

public class Director extends AngajatAbstract {
    public Director(String nume, String prenume, LocalDate dataNastere, LocalDate dataAngajare)
            throws InvalidUserInputException {
        super(nume, prenume, dataNastere, dataAngajare);
        this.coeficient = 2;

    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + "FUNCTIE: " + "Director\n";
    }

}
