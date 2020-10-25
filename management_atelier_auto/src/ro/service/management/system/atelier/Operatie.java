package ro.service.management.system.atelier;

import ro.service.management.system.masina.MasinaAbstract;

public class Operatie {
    private final MasinaAbstract masina;
    private final int durata;

    public Operatie(MasinaAbstract masina, int durata) {
        this.masina = masina;
        this.durata = durata;
    }

    public int getDurata() {
        return durata;
    }

    public MasinaAbstract getMasina() {
        return masina;
    }

    @Override
    public String toString() {
        return "ID MASINA: " + masina.getId() + "\n"
                + "DURATA: " + ((float) durata) / 10000 + " minute " + "\n";
    }

}
