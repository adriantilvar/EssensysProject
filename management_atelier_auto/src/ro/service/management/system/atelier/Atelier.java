package ro.service.management.system.atelier;

import ro.service.management.system.angajat.AngajatAbstract;
import ro.service.management.system.masina.MasinaAbstract;

import java.util.*;


public class Atelier {
    private boolean esteDeschis = false;
    protected final ArrayList<AngajatAbstract> listaAngajati = new ArrayList<>();
    private final ArrayList<SituatieAngajat> listaSituatiiAngajati = new ArrayList<>();
    private final Map<Operatie, Integer> listaOperatiiAtelier = new HashMap<>();
    private final Queue<Operatie> coadaOperatii = new LinkedList<>();

    public ArrayList<AngajatAbstract> getListaAngajati() {
        return listaAngajati;
    }

    public boolean esteDeschis() {
        return esteDeschis;
    }


    public void adaugaAngajat(AngajatAbstract a) {
        listaAngajati.add(a);
        listaSituatiiAngajati.add(new SituatieAngajat(a.getId()));

        if (!esteDeschis) esteDeschis = true;
    }


    public void stergereAngajat(int id) {
        for(AngajatAbstract a : listaAngajati) {
            if(a.getId() == id) {
                listaAngajati.remove(a);
                listaSituatiiAngajati.removeIf(s -> s.getId() == id);
                while (listaOperatiiAtelier.values().remove(id));

                System.out.println("Angajat sters.");
                break;
            }
        }

        System.out.println("Nici un angajat nu a fost sters.");
    }


    public void editareAngajat(int index, AngajatAbstract angajat) {
        listaAngajati.set(index, angajat);

    }


    public void afisareAngajati() {
        if(listaAngajati.isEmpty()) {
            System.out.println("\nNu exista angajati.\n");
        } else {
            for (AngajatAbstract a : listaAngajati) {
                String afisare = "\n"
                        + "ID: " + a.getId() + "\n"
                        + "NUME: " + a.getNume() + "\n"
                        + "PRENUME: " + a.getPrenume() + "\n";
                System.out.println(afisare);
            }
        }
    }


    public boolean preiaMasina(Operatie operatie) throws Exception {
        boolean estePlasat = false;

        if (!esteDeschis) {
            System.out.println("Atelier inchis momentan.");
            return false;
        }


        SituatieAngajat angajat = verificaDisponibilitateAngajati(operatie);

        if (angajat != null) {
            angajat.adaugaMasina(operatie.getMasina());
            listaOperatiiAtelier.put(operatie, angajat.getId());
            estePlasat = true;
        }

        if (estePlasat) {
            anuntaTerminareOperatie(operatie);
        } else {
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Nici un angajat nu poate prelua automobilul.\n");
            afisareCoada();
        }

        return estePlasat;
    }


    public boolean preiaMasinaAngajatSpecific(Operatie operatie, int idAngajat) throws Exception {
        boolean estePlasat = false;

        if (!esteDeschis) {
            System.out.println("Atelier inchis momentan.");
            return false;
        }

        SituatieAngajat angajat = verificaDisponibilitateAngajati(operatie, idAngajat);

        if (angajat != null) {
            angajat.adaugaMasina(operatie.getMasina());
            listaOperatiiAtelier.put(operatie, angajat.getId());
            estePlasat = true;
        }

        if (estePlasat) {
            anuntaTerminareOperatie(operatie);
        } else {
            System.out.println("\nAngajatul cu id-ul " + idAngajat + " nu poate prelua automobilul.\n");
            afisareCoada();
        }

        return estePlasat;
    }


    public void adaugaInCoada(Operatie operatie) {
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("\nAutomobilul " + operatie.getMasina().getId() + " adaugat in coada.\n");
        coadaOperatii.add(operatie);
        afisareCoada();
    }


    public void scoateDinCoada() throws Exception {
        if (!coadaOperatii.isEmpty()) {
            Operatie urmatoareaMasina = coadaOperatii.element();

            if (verificaDisponibilitateAngajati(urmatoareaMasina) != null) {
                coadaOperatii.remove();
                preiaMasina(urmatoareaMasina);

            } else System.out.println("\nMasina nu poate fi preluata inca.\n");

        } else System.out.println("\nCoada goala!\n");
    }


    private void anuntaTerminareOperatie(Operatie operatie) throws Exception {
        if (operatie != null) {
            Timer t = new Timer();

            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    SituatieAngajat situatie = gasesteSituatie(operatie.getMasina());

                    if (situatie != null) {
                        situatie.eliminareMasina(operatie.getMasina().getId());
                        listaOperatiiAtelier.remove(operatie);
                        System.out.println("\nMasina cu id-ul " + operatie.getMasina().getId() + " este gata!\n");

                        try {
                            scoateDinCoada();
                        } catch (Exception e) {
                            System.out.println("\nEroare la scoaterea din coada!\n");
                        }

                    } else System.out.println("\nMasina nu a fost gasita!\n");

                    t.cancel();
                }
            }, operatie.getDurata());


        } else throw new Exception("\nMetoda nu poate fi apelata cu argument null!\n");
    }


    private SituatieAngajat verificaDisponibilitateAngajati(Operatie operatie) {
        SituatieAngajat locDisponibil = null;

        for (SituatieAngajat s : listaSituatiiAngajati) {
            if (s.verificaDisponibilitate(MasinaAbstract.identificareTipMasina(operatie.getMasina()))) {
                locDisponibil = s;
            }
        }

        return locDisponibil;
    }


    private SituatieAngajat verificaDisponibilitateAngajati(Operatie operatie, int id) {
        for (SituatieAngajat s : listaSituatiiAngajati) {
            if (s.getId() == id) {
                if (s.verificaDisponibilitate(MasinaAbstract.identificareTipMasina(operatie.getMasina())))
                    return s;
            }
        }

        return null;
    }


    private SituatieAngajat gasesteSituatie(MasinaAbstract masina) {
        SituatieAngajat situatie = null;

        for (SituatieAngajat s : listaSituatiiAngajati) {
            for (MasinaAbstract m : s.listaMasini) {
                if (m == masina) {
                    situatie = s;
                    break;
                }
            }
        }
        return situatie;
    }


    private void afisareCoada() {
        System.out.println("INFO COADA:");
        System.out.println("***************");
        if (coadaOperatii.isEmpty()) System.out.println("Nici un automobil in coada.\n");
        else {
            for (Operatie o : coadaOperatii) {
                System.out.println(o.toString());
            }
            System.out.println("\n");
        }
    }

}
