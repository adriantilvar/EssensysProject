package ro.service.management.system;

import ro.service.management.system.angajat.AngajatAbstract;
import ro.service.management.system.angajat.Asistent;
import ro.service.management.system.angajat.Director;
import ro.service.management.system.angajat.Mecanic;
import ro.service.management.system.atelier.Atelier;
import ro.service.management.system.atelier.Operatie;
import ro.service.management.system.exceptii.InvalidUserInputException;
import ro.service.management.system.masina.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Atelier atelier = new Atelier();
        Scanner in = new Scanner(System.in);

        meniuPrincipal(in, atelier);

    }


    private static void meniuPrincipal(Scanner in, Atelier atelier) throws Exception {
        clearConsole();
        System.out.println("Meniu principal");
        System.out.println("***************");
        System.out.println();
        System.out.println("Selectati una dintre optiuni:");
        System.out.println("AGAJATI - 1");
        System.out.println("MASINI - 2");
        System.out.println("ATELIER AUTO - 3");
        System.out.println("IESIRE - 4");
        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);

            switch (optiune) {
                case 1:
                    intampinareAngajati(in, atelier);
                    break;

                case 2:
                    intampinareMasini(in, atelier);
                    break;

                case 3:
                    intampinareAtelier(in, atelier);
                    break;

                case 4:
                    break;
                default:
                    meniuPrincipal(in, atelier);
                    break;

            }
        } catch (NumberFormatException e) {
            meniuPrincipal(in, atelier);
        }

    }


    private static void revenireMeniu(Scanner input, Atelier atelier) throws Exception {
        meniuPrincipal(input, atelier);
    }


    private static void intampinareAngajati(Scanner in, Atelier atelier) throws Exception {
        clearConsole();
        System.out.println("\nMeniu angajati");
        System.out.println("**************");
        System.out.println();
        System.out.println("Alegeti una dintre optiuni:");
        System.out.println("ADAUGARE ANGAJAT - 1");
        System.out.println("EDITARE ANGAJAT - 2");
        System.out.println("STERGERE ANGAJAT - 3");
        System.out.println("AFISARE ANGAJATI - 4");
        System.out.println("REVENIRE MENIU PRINCIPAL - 5");

        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);

            switch (optiune) {
                case 1:
                    clearConsole();
                    AngajatAbstract a = creeazaAngajat(in);

                    if(a != null) atelier.adaugaAngajat(a);
                    else System.out.println("Angajatul nu a fost adaugat!");

                    intampinareAngajati(in, atelier);
                    break;

                case 2:
                    editareAngajat(in, atelier);
                    intampinareAngajati(in, atelier);
                    break;

                case 3:
                    stergeAngajat(in, atelier);
                    intampinareAngajati(in, atelier);
                    break;

                case 4:
                    afiseazaAngajati(in, atelier);
                    break;

                case 5:
                    revenireMeniu(in, atelier);
                    break;

                default:
                    intampinareAngajati(in, atelier);
                    break;

            }
        } catch (NumberFormatException e) {
            intampinareAngajati(in, atelier);
        }
    }


    private static void intampinareMasini(Scanner in, Atelier atelier) throws Exception {
        clearConsole();
        System.out.println("Meniu masini");
        System.out.println("**************");
        System.out.println();
        System.out.println("Alegeti una dintre optiuni:");
        System.out.println("CALCULARE VALOARE POLITA - 1");
        System.out.println("REVENIRE MENIU PRINCIPAL - 2");
        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);

            switch (optiune) {
                case 1:
                    calculareValoarePolita(in, atelier);
                    break;
                case 2:
                    revenireMeniu(in, atelier);
                    break;
                default:
                    intampinareMasini(in, atelier);
                    break;

            }
        } catch (NumberFormatException e) {
            intampinareMasini(in, atelier);
        }
    }


    private static void calculareValoarePolita(Scanner in, Atelier atelier) throws Exception {
        boolean discount = aplicareDiscount(in);
        clearConsole();
        MasinaAbstract masina = creeazaMasina(in);

        if(masina != null) {
            clearConsole();
            System.out.println("VALOARE POLITA:");
            System.out.println(masina.calculPolitaAsigurare(discount));

            System.out.println("\nREVENIRE MENIU MASINI - 1");
            System.out.println("REVENIRE MENIU PRINCIPAL - 2");
            String raspuns = in.nextLine();

            try {
                int optiune = Integer.parseInt(raspuns);
                if(optiune == 1) intampinareMasini(in, atelier);
                else revenireMeniu(in, atelier);

            } catch (NumberFormatException e) {
                revenireMeniu(in, atelier);
            }

        } else {
            System.out.println("Nu s-a putut calcula polita de asigurare.");
        }

    }


    private static boolean aplicareDiscount(Scanner in) {
        clearConsole();
        System.out.println("Se aplica discount?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");
        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);
            return optiune == 1;

        } catch (NumberFormatException e) {
            aplicareDiscount(in);
            return false;
        }
    }


    private static void intampinareAtelier(Scanner input, Atelier atelier) throws Exception {
        clearConsole();
        mesajInformativ();
        if (atelier.esteDeschis()) {
            alegereAngajat(input, atelier);
        } else {
            System.out.println("Ne pare rau, atelier inchis momentan. Reveniti mai tarziu.");
        }

    }


    private static void alegereAngajat(Scanner input, Atelier atelier) throws Exception {
        System.out.println("Doriti ca masina dumneavoastra sa fie preluata de catre un anumit angajat?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");

        try {
            if (Integer.parseInt(input.nextLine()) == 1) {
                clearConsole();
                preluareSpecifica(input, atelier);
            } else {
                clearConsole();
                preluareStandard(input, atelier);
            }
        } catch (NumberFormatException e) {
            clearConsole();
            System.out.println("Introduceti cifra aferenta optiunii.");
            alegereAngajat(input, atelier);
        }
    }


    private static void preluareStandard(Scanner input, Atelier atelier) throws Exception {
        System.out.println("Date referitoare la automobilul dumneavoastra");
        System.out.println("*********************************************\n");
        MasinaAbstract masina = creeazaMasina(input);
        int durata = introduceDurata(input);
        clearConsole();

        if (masina != null) {
            Operatie operatieCurenta = new Operatie(masina, durata);

            if (!atelier.preiaMasina(operatieCurenta)) {
                intrareCoada(input, atelier, operatieCurenta);

            } else introducereMasinaExtra(input, atelier);

        } else {
            System.out.println("Datele automobilului au fost introduse gresit!");
            intampinareAtelier(input, atelier);
        }
    }


    private static void preluareStandard(Scanner input, Atelier atelier, Operatie operatie) throws Exception {
        if (!atelier.preiaMasina(operatie)) {
            intrareCoada(input, atelier, operatie);
        }
    }


    private static void preluareSpecifica(Scanner input, Atelier atelier) throws Exception {
        atelier.afisareAngajati();
        System.out.println("Introduceti id-ul angajatului dorit:");
        String raspuns = input.nextLine();

        try {
            int idAngajatDorit = Integer.parseInt(raspuns);
            MasinaAbstract masina = creeazaMasina(input);
            int durata = introduceDurata(input);
            clearConsole();

            if (masina != null) {
                Operatie operatieCurenta = new Operatie(masina, durata);

                if (!atelier.preiaMasinaAngajatSpecific(operatieCurenta, idAngajatDorit)) {
                    angajatIndisponibil(input, atelier, operatieCurenta);
                } else introducereMasinaExtra(input, atelier);

            }
        } catch (NumberFormatException e) {
            preluareSpecifica(input, atelier);
        }
    }


    private static void intrareCoada(Scanner input, Atelier atelier, Operatie operatie) throws Exception {
        System.out.println("Va rugam introduceti una dintre optiunile valide.");
        System.out.println("Doriti sa asteptati la coada?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");

        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) atelier.adaugaInCoada(operatie);
            else if (Integer.parseInt(raspuns) == 2) {
                clearConsole();
                System.out.println("Ne pare rau pentru disconfortul creat!");
                System.out.println("O zi frumoasa!");
            } else {
                intrareCoada(input, atelier, operatie);
            }

            introducereMasinaExtra(input, atelier);

        } catch (NumberFormatException e) {
            intrareCoada(input, atelier, operatie);
        }
    }


    private static MasinaAbstract creeazaMasina(Scanner input) {
        String tip = alegereTipAutomobil(input);
        int id = alegereIdAutomobil(input);
        int nrKm = alegereKilometraj(input);
        int anFabricatie = alegereAnFabricatie(input);
        boolean esteDiesel = alegereEsteDiesel(input);

        if (tip != null && id != -1 && nrKm != -1 && anFabricatie != -1) {
            switch (tip) {
                case "masina":
                    ModTransmisie mod = alegereModTransmisie(input);
                    if (mod != null) return new MasinaStandard(id, nrKm, anFabricatie, esteDiesel, mod);
                case "autobuz":
                    int nrLocuri = alegereNumarLocuri(input);
                    if (nrLocuri != -1) return new Autobuz(id, nrKm, anFabricatie, esteDiesel, nrLocuri);
                case "camion":
                    int tonaj = alegereTonaj(input);
                    if (tonaj != -1) return new Camion(id, nrKm, anFabricatie, esteDiesel, tonaj);
                default:
                    return null;
            }
        } else return null;
    }


    private static AngajatAbstract creeazaAngajat(Scanner input) throws InvalidUserInputException {
        String tip = alegereTipAngajat(input);
        String nume = alegereNume(input);
        String prenume = alegerePrenume(input);
        LocalDate dataNastere = alegereDataNastere(input);
        LocalDate dataAngajare = alegereDataAngajare(input);


        if(tip != null) {
            switch (tip) {
                case "director":
                    return new Director(nume, prenume, dataNastere, dataAngajare);
                case "mecanic":
                    return new Mecanic(nume, prenume, dataNastere, dataAngajare);
                case "asistent":
                    return new Asistent(nume, prenume, dataNastere, dataAngajare);
                default:
                    return null;
            }

        } else {
            System.out.println("Tip angajat invalid.");
            return null;
        }
    }


    private static void afiseazaAngajati(Scanner input, Atelier atelier) throws Exception {
        clearConsole();
        atelier.afisareAngajati();

        System.out.println("\nREVENIRE MENIU ANGAJATI - 1");
        System.out.println("REVENIRE MENIU PRINCIPAL - 2");
        String raspuns = input.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);
            if(optiune == 1) intampinareAngajati(input, atelier);
            else revenireMeniu(input, atelier);

        } catch (NumberFormatException e) {
            revenireMeniu(input, atelier);
        }
    }


    private static void stergeAngajat(Scanner in, Atelier atelier) {
        clearConsole();
        System.out.println("Introduceti id-ul angajatului:");
        String raspuns = in.nextLine();

        try {
            atelier.stergereAngajat(Integer.parseInt(raspuns));

        } catch (NumberFormatException e) {
            stergeAngajat(in, atelier);
        }
    }


    private static void editareAngajat(Scanner in, Atelier atelier) throws Exception {
        clearConsole();
        ArrayList<AngajatAbstract> lista = atelier.getListaAngajati();
        AngajatAbstract modificareProvizorie = null;
        boolean esteAngajatModificat = false;

        System.out.println("Introduceti id-ul angajatului:");
        String raspuns = in.nextLine();

        try {
            int id = Integer.parseInt(raspuns);

            for(AngajatAbstract a : lista) {
                if(a.getId() == id) {
                    modificareProvizorie = modificareCamp(in, atelier, a);
                    atelier.editareAngajat(lista.indexOf(a), modificareProvizorie);
                    esteAngajatModificat = true;
                    break;
                }
            }

        } catch (NumberFormatException e) {
            editareAngajat(in, atelier);
        }

        if(esteAngajatModificat && modificareProvizorie != null) {
            modificareExtra(in, atelier, modificareProvizorie);
        }
    }


    private static void modificareExtra(Scanner in, Atelier atelier, AngajatAbstract angajat) throws Exception {
        clearConsole();
        System.out.println("Doriti sa mai modificati vreun camp?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");
        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);

            if(optiune == 1) modificareCamp(in, atelier, angajat);
            else if(optiune == 2) intampinareAngajati(in, atelier);

        } catch (NumberFormatException e) {
            modificareExtra(in, atelier, angajat);
        }
    }


    private static AngajatAbstract modificareCamp(Scanner in, Atelier atelier, AngajatAbstract angajat) throws Exception {
        clearConsole();
        System.out.println("Ce camp doriti sa modificati?");
        System.out.println("NUME - 1");
        System.out.println("PRENUME - 2");
        System.out.println("DATA NASTERE - 3");
        System.out.println("DATA ANGAjARE - 4");
        System.out.println("RENUNTA - 5");
        String raspuns = in.nextLine();

        try {
            int optiune = Integer.parseInt(raspuns);

            switch (optiune) {
                case 1:
                    String nume = alegereNume(in);
                    if(nume != null)  {
                        angajat.setNume(nume);
                        return angajat;
                    } else return null;
                case 2:
                    String prenume = alegerePrenume(in);
                    if(prenume != null)  {
                        angajat.setPrenume(prenume);
                        return angajat;
                    } else return null;
                case 3:
                    LocalDate dataNastere = alegereDataNastere(in);
                    if(dataNastere != null)  {
                        angajat.setDataNastere(dataNastere);
                        return angajat;
                    } else return null;
                case 4:
                    LocalDate dataAngajare = alegereDataAngajare(in);
                    if(dataAngajare != null)  {
                        angajat.setDataNastere(dataAngajare);
                        return angajat;
                    } else return null;
                case 5:
                    intampinareAngajati(in, atelier);
                    return null;
                default:
                    return null;
            }

        } catch (NumberFormatException e) {
            modificareCamp(in, atelier, angajat);
        }

        return null;
    }


    private static int introduceDurata(Scanner input) {
        clearConsole();
        System.out.println("Atentie! Doar personalul autorizat poate raspunde!");
        System.out.println("Introduceti durata reparatiei:");
        String raspuns = input.nextLine();

        try {
            return Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            introduceDurata(input);
            return -1;
        }
    }


    private static String alegereTipAutomobil(Scanner input) {
        System.out.println("Introduceti cifra aferenta optiunii.");
        System.out.println("Introduceti tipul automobilului");
        System.out.println("Masina - 1");
        System.out.println("Autobuz - 2");
        System.out.println("Camion - 3");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) return "masina";
            else if (Integer.parseInt(raspuns) == 2) return "autobuz";
            else if (Integer.parseInt(raspuns) == 3) return "camion";
            else {
                alegereTipAutomobil(input);
                return null;
            }
        } catch (NumberFormatException e) {
            clearConsole();
            alegereTipAutomobil(input);
            return null;
        }
    }


    private static String alegereTipAngajat(Scanner input) {
        System.out.println("\nIntroduceti cifra aferenta optiunii.");
        System.out.println("Introduceti tipul automobilului");
        System.out.println("Director - 1");
        System.out.println("Mecanic - 2");
        System.out.println("Asistent - 3");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) return "director";
            else if (Integer.parseInt(raspuns) == 2) return "mecanic";
            else if (Integer.parseInt(raspuns) == 3) return "asistent";
            else {
                alegereTipAngajat(input);
                return null;
            }
        } catch (NumberFormatException e) {
            clearConsole();
            alegereTipAngajat(input);
            return null;
        }
    }


    private static String alegereNume(Scanner input) {
        clearConsole();
        System.out.println("Introduceti numele angajatului:");
        String raspuns = input.nextLine();

        if(raspuns == null || raspuns.isEmpty()) {
            alegereNume(input);
            return null;

        } else if(raspuns.length() > 30) {
            System.out.println("Numele nu trebuie sa depaseasca 30 de caractere!");
            alegereNume(input);
            return null;
        }

        return raspuns;
    }


    private static String alegerePrenume(Scanner input) {
        clearConsole();
        System.out.println("Introduceti prenumele angajatului:");
        String raspuns = input.nextLine();

        if(raspuns == null || raspuns.isEmpty()) {
            alegerePrenume(input);
            return null;

        } else if(raspuns.length() > 30) {
            System.out.println("Prenumele nu trebuie sa depaseasca 30 de caractere!");
            alegerePrenume(input);
            return null;
        }

        return raspuns;
    }


    private static LocalDate alegereDataNastere(Scanner input) {
        LocalDate data = null;

        clearConsole();
        System.out.println("Data nasterii:");
        int an = alegereAn(input);
        int luna = alegereLuna(input);
        int zi = alegereZi(input);

        if(an != -1 && luna != -1 && zi != -1) {
            if(LocalDate.now().getYear() - an >= 18) {
                return LocalDate.of(an, luna, zi);
            } else {
                System.out.println("Ne pare rau, trebuie sa aveti cel putin 18 ani.");
            }

        } else System.out.println("Data invalida.");

        return data;
    }


    private static LocalDate alegereDataAngajare(Scanner input) {
        LocalDate data = null;

        clearConsole();
        System.out.println("Data angajarii:");
        System.out.println("Introduceti cifra aferenta optiunii.");
        System.out.println("Data de angajare este astazi?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) data = LocalDate.now();
            else if (Integer.parseInt(raspuns) == 2) {
                int an = alegereAn(input);
                int luna = alegereLuna(input);
                int zi = alegereZi(input);

                if(an != -1 && luna != -1 && zi != -1) {

                    if(LocalDate.of(an, luna, zi).isBefore(LocalDate.now())) {
                        data = LocalDate.of(an, luna, zi);
                    } else {
                        System.out.println("Data nu poate angajarii nu poate fi o data ulterioara.");
                    }

                } else System.out.println("Data invalida.");
            }
            else alegereDataAngajare(input);

        } catch (NumberFormatException e) {
            alegereDataAngajare(input);

        }

        return data;

    }


    private static int alegereAn(Scanner input) {
        System.out.println("\nIntroduceti un an valid:");
        String raspuns = input.nextLine();

        if(raspuns.length() == 4) {
            try {
                int an = Integer.parseInt(raspuns);

                if(an > 1800 && an <= LocalDate.now().getYear()) return an;
                else {
                    alegereAn(input);
                    return -1;
                }

            } catch (NumberFormatException e) {
                alegereAn(input);
                return -1;
            }
        } else alegereAn(input);

        return -1;
    }


    private static int alegereLuna(Scanner input) {
        System.out.println("\nIntroduceti cifra aferenta unei luni(1-12):");
        String raspuns = input.nextLine();

        if(raspuns.length() >= 1 && raspuns.length() <= 2) {
            try {
                int luna = Integer.parseInt(raspuns);

                if(luna >= 1 && luna <= 12) return luna;
                else {
                    alegereLuna(input);
                    return -1;
                }

            } catch (NumberFormatException e) {
                alegereLuna(input);
                return -1;
            }
        } else alegereLuna(input);

        return -1;
    }


    private static int alegereZi(Scanner input) {
        System.out.println("\nIntroduceti o zi valida(1-31):");
        String raspuns = input.nextLine();

        if(raspuns.length() >= 1 && raspuns.length() <= 2) {
            try {
                int zi = Integer.parseInt(raspuns);

                if(zi >= 1 && zi <= 31) return zi;
                else {
                    alegereZi(input);
                    return -1;
                }

            } catch (NumberFormatException e) {
                alegereZi(input);
                return -1;
            }
        } else alegereZi(input);

        return -1;
    }


    private static int alegereIdAutomobil(Scanner input) {
        clearConsole();
        System.out.println("Va rugam introduceti DOAR cifre.");
        System.out.println("Introduceti id-ul automobilului:");
        String raspuns = input.nextLine();

        try {
            return Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            alegereIdAutomobil(input);
            return -1;
        }
    }


    private static int alegereKilometraj(Scanner input) {
        clearConsole();
        System.out.println("Va rugam introduceti DOAR cifre.");
        System.out.println("Introduceti kilometrajul automobilului:");
        String raspuns = input.nextLine();

        try {
            return Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            alegereKilometraj(input);
            return -1;
        }
    }


    private static int alegereAnFabricatie(Scanner input) {
        clearConsole();
        System.out.println("Va rugam introduceti un an valid alcatuit DOAR din 4 cifre.");
        System.out.println("Introduceti in ce an a fost fabricat automobilul:");
        String raspuns = input.nextLine();

        if (raspuns.length() == 4) {
            try {
                int an = Integer.parseInt(raspuns);

                if (an > 1892 && an < LocalDate.now().getYear()) return an;
                else {
                    alegereAnFabricatie(input);
                    return -1;
                }

            } catch (NumberFormatException e) {
                alegereAnFabricatie(input);
                return -1;
            }
        } else {
            alegereAnFabricatie(input);
            return -1;
        }
    }


    private static boolean alegereEsteDiesel(Scanner input) {
        clearConsole();
        System.out.println("Introduceti cifra aferenta optiunii.");
        System.out.println("Autoturismul este Diesel?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) return true;
            else if (Integer.parseInt(raspuns) == 2) return false;
            else {
                alegereEsteDiesel(input);
                return false;
            }

        } catch (NumberFormatException e) {
            alegereEsteDiesel(input);
            return false;
        }
    }


    private static ModTransmisie alegereModTransmisie(Scanner input) {
        clearConsole();
        System.out.println("Introduceti cifra aferenta optiunii.");
        System.out.println("Ce fel de transmisie are automobilul?");
        System.out.println("MANUAL - 1");
        System.out.println("AUTOMAT - 2");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) return ModTransmisie.MANUAL;
            else if (Integer.parseInt(raspuns) == 2) return ModTransmisie.AUTOMAT;
            else {
                alegereModTransmisie(input);
                return null;
            }

        } catch (NumberFormatException e) {
            alegereModTransmisie(input);
            return null;
        }
    }


    private static int alegereNumarLocuri(Scanner input) {
        clearConsole();
        System.out.println("Va rugam introduceti DOAR cifre.");
        System.out.println("Introduceti numarul de locuri al automobilului:");
        String raspuns = input.nextLine();

        try {
            return Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            alegereNumarLocuri(input);
            return -1;
        }
    }


    private static int alegereTonaj(Scanner input) {
        clearConsole();
        System.out.println("Va rugam introduceti DOAR cifre.");
        System.out.println("Introduceti tonajul automobilului:");
        String raspuns = input.nextLine();

        try {
            return Integer.parseInt(raspuns);
        } catch (NumberFormatException e) {
            alegereTonaj(input);
            return -1;
        }
    }


    private static void clearConsole() {

        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }


    private static void angajatIndisponibil(Scanner input, Atelier atelier, Operatie operatie) throws Exception {
        System.out.println("\nIntroduceti cifra aferenta optiunii.");
        System.out.println("\nDoriti plasarea la un angajat liber?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");

        String optiune = input.nextLine();

        try {
            if (Integer.parseInt(optiune) == 1) preluareStandard(input, atelier, operatie);
            else if (Integer.parseInt(optiune) == 2) {
                clearConsole();
                System.out.println("Din pacate nu se poate astepta pentru plasarea la un angajat specific.");

            }

        } catch (NumberFormatException e) {
            angajatIndisponibil(input, atelier, operatie);
        }
    }


    private static void introducereMasinaExtra(Scanner input, Atelier atelier) throws Exception {
        System.out.println("\nIntroduceti cifra aferenta optiunii.");
        System.out.println("Doriti sa introduceti alt automobil?");
        System.out.println("DA - 1");
        System.out.println("NU - 2");
        String raspuns = input.nextLine();

        try {
            if (Integer.parseInt(raspuns) == 1) intampinareAtelier(input, atelier);
            else if (Integer.parseInt(raspuns) == 2) {
                clearConsole();
                System.out.println("O zi frumoasa!");
            } else introducereMasinaExtra(input, atelier);

        } catch (NumberFormatException e) {
            introducereMasinaExtra(input, atelier);
        }
    }


    private static void mesajInformativ() {
        System.out.println("Bine ati venit in atelier!");
        System.out.println("*****************************************************************");
        System.out.println("Utilizarea aplicatiei:");
        System.out.println("1. Pentru alegerea unei optiuni, introduceti doar cifra aferenta.");
        System.out.println("2. Dupa introducerea raspunsului, apasati tasta ENTER.");
        System.out.println("*****************************************************************\n");
    }

}
