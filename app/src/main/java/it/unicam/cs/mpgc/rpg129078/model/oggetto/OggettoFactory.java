package it.unicam.cs.mpgc.rpg129078.model.oggetto;

public class OggettoFactory {

    public static Oggetto crea(String classe) {
        return switch (classe) {
            case "Snack"           -> new Snack();
            case "ChiavettaUsb"    -> new ChiavettaUsb();
            case "ManualeAziendale"-> new ManualeAziendale();
            case "RaccogliBadgeAziendale"  -> new RaccogliBadgeAziendale();
            case "RaccogliLaptopAziendale" -> new RaccogliLaptopAziendale();
            default -> throw new IllegalArgumentException("Oggetto sconosciuto: " + classe);
        };
    }


}
