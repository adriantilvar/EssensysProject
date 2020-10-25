package ro.service.management.system.angajat;

import ro.service.management.system.exceptii.InvalidUserInputException;

import java.time.LocalDate;

public class Mecanic extends AngajatAbstract {
    public Mecanic(String nume, String prenume, LocalDate dataNastere, LocalDate dataAngajare)
            throws InvalidUserInputException {
        super(nume, prenume, dataNastere, dataAngajare);
        this.coeficient = 1.5f;

    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString + "FUNCTIE: " + "Mecanic\n";
    }

}
