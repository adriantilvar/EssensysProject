package ro.service.management.system.masina;

import java.time.LocalDate;

public class MasinaStandard extends MasinaAbstract {
    private ModTransmisie transmisie;

    public MasinaStandard(int id, int nrKilometri, int anFabricatie, boolean esteDiesel, ModTransmisie transmisie) {
        super(id, nrKilometri, anFabricatie, esteDiesel);
        this.transmisie = transmisie;

    }

    @Override
    public float calculPolitaAsigurare(boolean discount) {
        int vechime = LocalDate.now().getYear() - this.anFabricatie;
        float valoare = vechime * 100;

        if (this.esteDiesel)
            valoare += 500;

        if (this.nrKilometri >= 200000)
            valoare += 500;

        if (discount) {
            valoare *= 0.95;
        }

        return valoare;
    }
}
