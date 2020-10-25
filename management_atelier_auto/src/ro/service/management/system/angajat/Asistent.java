package ro.service.management.system.angajat;

import ro.service.management.system.exceptii.InvalidUserInputException;

import java.time.LocalDate;

public class Asistent extends AngajatAbstract {
    public Asistent(String nume, String prenume, LocalDate dataNastere, LocalDate dataAngajare)
            throws InvalidUserInputException {
        super(nume, prenume, dataNastere, dataAngajare);
        this.coeficient = 1;

    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + "FUNCTIE: " + "Asistent\n";
    }

}
