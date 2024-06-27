package com.mygdx.game;

public class ConfiguracionGlobal {
    private static ConfiguracionGlobal instance;
    private int volume;
    private String playerName;

    // Constructor privado para evitar la instanciación directa
    private ConfiguracionGlobal() {
        // Inicializar configuraciones por defecto
        volume = 100;
        playerName = "Jugador";
    }

    // Método estático para obtener la instancia única de la clase
    public static synchronized ConfiguracionGlobal getInstance() {
        if (instance == null) {
            instance = new ConfiguracionGlobal();
        }
        return instance;
    }

    // Métodos para obtener y establecer configuraciones
    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
