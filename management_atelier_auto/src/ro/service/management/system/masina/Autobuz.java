package ro.service.management.system.masina;

import java.time.LocalDate;

public class Autobuz extends MasinaAbstract {
    private int nrLocuri;

    public Autobuz(int id, int nrKilometri, int anFabricatie, boolean esteDiesel, int nrLocuri) {
        super(id, nrKilometri, anFabricatie, esteDiesel);
        this.nrLocuri = nrLocuri;

    }

    @Override
    public float calculPolitaAsigurare(boolean discount) {
        int vechime = LocalDate.now().getYear() - this.anFabricatie;
        float valoare = vechime * 200;

        if (this.esteDiesel)
            valoare += 1000;

        if (this.nrKilometri >= 200000)
            valoare += 1000;
        else if (this.nrKilometri >= 100000)
            valoare += 500;

        if (discount) {
            valoare *= 0.9;
        }

        return valoare;
    }
}
