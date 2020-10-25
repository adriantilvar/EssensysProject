package ro.service.management.system.atelier;

import ro.service.management.system.masina.MasinaAbstract;

import java.util.ArrayList;

public class SituatieAngajat {
    private int id;
    protected ArrayList<MasinaAbstract> listaMasini = new ArrayList<>();
    private int contorMasini = 0;
    private int contorAutobuz = 0;
    private int contorCamion = 0;

    public SituatieAngajat(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }


    public void adaugaMasina(MasinaAbstract m) {
        if (contorMasini == 3 && contorAutobuz == 1 && contorCamion == 1) {
            System.out.println("\nAngajat complet ocupat.\n");

        } else {
            if (verificaDisponibilitate(MasinaAbstract.identificareTipMasina(m)))
                preluareMasina(m);
            else System.out.println("\nAngajatul nu poate prelua automobilul.\n");

        }
    }


    public void eliminareMasina(int id) {

        MasinaAbstract masinaEliminata = null;

        for (MasinaAbstract m : listaMasini) {
            if (m.getId() == id) {
                masinaEliminata = m;
            }
        }

        if (masinaEliminata != null) {
            listaMasini.remove(masinaEliminata);
            decrementareContor(MasinaAbstract.identificareTipMasina(masinaEliminata));

        } else System.out.println("Automobilul nu a fost gasit.");

    }


    public boolean verificaDisponibilitate(String tip) {
        if (contorMasini == 3 && contorAutobuz == 1 && contorCamion == 1) {
            return false;
        }

        switch (tip) {
            case "masina":
                return contorMasini < 3;
            case "autobuz":
                return contorAutobuz < 1;
            case "camion":
                return contorCamion < 1;
            default:
                throw new UnsupportedOperationException();
        }

    }


    private void preluareMasina(MasinaAbstract m) {
        listaMasini.add(m);
        incrementareContor(MasinaAbstract.identificareTipMasina(m));
        System.out.println("\nAutomobilul a fost preluat de catre angajatul cu id-ul " + this.id + "\n");

    }


    private void incrementareContor(String tip) {
        switch (tip) {
            case "masina":
                contorMasini++;
                break;
            case "autobuz":
                contorAutobuz++;
                break;
            case "camion":
                contorCamion++;
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }


    private void decrementareContor(String tip) {
        switch (tip) {
            case "masina":
                contorMasini--;
                break;
            case "autobuz":
                contorAutobuz--;
                break;
            case "camion":
                contorCamion--;
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }


}
