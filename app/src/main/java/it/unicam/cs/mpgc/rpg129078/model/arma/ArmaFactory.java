package it.unicam.cs.mpgc.rpg129078.model.arma;

public class ArmaFactory {

    public static Arma crea(String classe, int livello) {
        Arma arma = switch (classe) {
            case "LaptopAziendale" -> new LaptopAziendale();
            case "BadgeAziendale"  -> new BadgeAziendale();
            case "Penna"           -> new Penna();
            default -> throw new IllegalArgumentException("Arma sconosciuta: " + classe);
        };
        if (arma instanceof Potenziabile p) p.setLivello(livello);
        return arma;
    }

}
