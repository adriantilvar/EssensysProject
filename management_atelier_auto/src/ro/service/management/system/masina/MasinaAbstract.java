package ro.service.management.system.masina;

public abstract class MasinaAbstract {
    protected int id;
    protected int nrKilometri;
    protected int anFabricatie;
    protected boolean esteDiesel;

    protected MasinaAbstract(int id, int nrKilometri, int anFabricatie, boolean esteDiesel) {
        this.id = id;
        this.nrKilometri = nrKilometri;
        this.anFabricatie = anFabricatie;
        this.esteDiesel = esteDiesel;
    }


    public int getId() {
        return id;
    }


    public abstract float calculPolitaAsigurare(boolean discount);


    public static String identificareTipMasina(MasinaAbstract m) {
        if (m instanceof MasinaStandard)
            return "masina";
        else if (m instanceof Autobuz)
            return "autobuz";
        else if (m instanceof Camion)
            return "camion";
        else throw new UnsupportedOperationException();

    }

}
