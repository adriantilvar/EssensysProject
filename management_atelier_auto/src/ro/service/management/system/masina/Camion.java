package ro.service.management.system.masina;

import java.time.LocalDate;

public class Camion extends MasinaAbstract {
    private int tonaj;

    public Camion(int id, int nrKilometri, int anFabricatie, boolean esteDiesel, int tonaj) {
        super(id, nrKilometri, anFabricatie, esteDiesel);
        this.tonaj = tonaj;

    }

    @Override
    public float calculPolitaAsigurare(boolean discount) {
        int vechime = LocalDate.now().getYear() - this.anFabricatie;
        float valoare = vechime * 300;

        if (this.nrKilometri >= 800000)
            valoare += 700;

        if (discount) {
            valoare *= 0.85;
        }

        return valoare;
    }
}
