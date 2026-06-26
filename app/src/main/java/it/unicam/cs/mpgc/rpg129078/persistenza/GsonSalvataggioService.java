package it.unicam.cs.mpgc.rpg129078.persistenza;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.*;


public class GsonSalvataggioService implements SalvataggioService{

    private static final String FILE_PATH = "salvataggio.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void salva(StatoPartita stato) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(stato, writer);
            System.out.println(">> Partita salvata!");
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
    }

    @Override
    public StatoPartita carica() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, StatoPartita.class);
        } catch (IOException e) {
            System.err.println("Errore caricamento: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean esisteSalvataggio() {
        return Files.exists(Paths.get(FILE_PATH));
    }




}
