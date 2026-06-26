package it.unicam.cs.mpgc.rpg129078.model.abilita;


public class AbilitaFactory {

    public static Abilita crea(String classe) {
        return switch (classe) {
            case "PausaCaffe"         -> new PausaCaffe();
            case "PremioProduzione"   -> new PremioProduzione();
            case "LavoroStraordinario"-> new LavoroStraordinario();
            default -> throw new IllegalArgumentException("Abilita sconosciuta: " + classe);
        };
    }

}
