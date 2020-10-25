package ro.service.management.system.angajat;

import ro.service.management.system.exceptii.InvalidUserInputException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public abstract class AngajatAbstract {
    protected int id;
    protected String nume;
    protected String prenume;
    protected LocalDate dataNastere;
    protected LocalDate dataAngajare;
    protected float coeficient;

    private static int contorId = 0;
    private static final ArrayList<AngajatAbstract> listaAngajati = new ArrayList<>();

    protected AngajatAbstract(String nume, String prenume, LocalDate dataNastere, LocalDate dataAngajare)
            throws InvalidUserInputException {

        this.id = ++contorId;

        if (nume == null || nume.isEmpty() || nume.length() > 30) {
            throw new InvalidUserInputException();

        } else this.nume = nume;

        if (prenume == null || prenume.isEmpty() || prenume.length() > 30) {
            throw new InvalidUserInputException();

        } else this.prenume = prenume;

        if (!verificaVarsta(dataNastere)) {
            throw new InvalidUserInputException();

        } else this.dataNastere = dataNastere;

        if (dataAngajare.isAfter(LocalDate.now())) {
            throw new InvalidUserInputException();

        } else this.dataAngajare = dataAngajare;

    }


    public int getId() {
        return id;
    }


    public String getNume() {
        return nume;
    }


    public String getPrenume() {
        return prenume;
    }


    public void setNume(String nume) {
        this.nume = nume;
    }


    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }


    public void setDataNastere(LocalDate dataNastere) {
        this.dataNastere = dataNastere;
    }


    public void setDataAngajare(LocalDate dataAngajare) {
        this.dataAngajare = dataAngajare;
    }


    public static void afisareAngajati() {
        if (listaAngajati.isEmpty()) {
            System.out.println("Nu exista angajati.\n");

        } else {
            System.out.println("Lista angajati:");
            System.out.println("*****************");

            for (AngajatAbstract a : listaAngajati)
                System.out.println(a.toString());
        }

    }


    public static void adaugareAngajat(AngajatAbstract a) {
        listaAngajati.add(a);

    }


    public static void stergereAngajat(int id) {
        if (gasesteAngajat(id) == null) {
            System.out.println("Nu exista un angajat cu id-ul " + id + ".\n");

        } else {
            listaAngajati.removeIf(a -> a.id == id);

        }

    }


    public static void editareAngajat(int id, AngajatAbstract angajatEditat) {
        AngajatAbstract angajatExistent = gasesteAngajat(id);

        if (angajatExistent == null) {
            System.out.println("Nu exista un ro.service.management.system.angajat cu id-ul " + id + ".\n");

        } else {
            angajatEditat.id = angajatExistent.id;
            listaAngajati.set(listaAngajati.indexOf(angajatExistent), angajatEditat);
        }

    }


    public static float calculareSalariu(int id) {
        AngajatAbstract a = gasesteAngajat(id);

        if (a == null) {
            System.out.println("Nu exista un ro.service.management.system.angajat cu id-ul " + id + ".\n");
            return -1;

        } else {
            int vechime = Period.between(a.dataAngajare, LocalDate.now()).getYears();
            return vechime * a.coeficient * 1000;

        }

    }


    private boolean verificaVarsta(LocalDate data) {
        Period perioada = Period.between(data, LocalDate.now());
        return perioada.getYears() > 18;
    }


    private static AngajatAbstract gasesteAngajat(int id) {
        for (AngajatAbstract a : listaAngajati) {
            if (a.id == id)
                return a;
        }

        return null;
    }


    @Override
    public String toString() {
        return "ID: " + this.id + "\n"
                + "NUME: " + this.nume + "\n"
                + "PRENUME: " + this.prenume + "\n"
                + "DATA NASTERII: " + this.dataNastere.toString() + "\n"
                + "DATA ANGAJARII: " + this.dataAngajare.toString() + "\n"
                + "COEFICIENT SALARIU: " + this.coeficient + "\n";
    }

}
