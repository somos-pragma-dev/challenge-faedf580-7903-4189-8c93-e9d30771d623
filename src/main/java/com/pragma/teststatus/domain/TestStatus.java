package com.pragma.teststatus.domain;

public enum TestStatus {
    PENDIENTE("pendiente"),
    EN_PROGRESO("en progreso"),
    FALLIDA("fallida"),
    EXITOSA("exitosa"),
    CANCELADA("cancelada");
    
    private final String displayName;
    
    TestStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isTerminal() {
        return this == FALLIDA || this == EXITOSA || this == CANCELADA;
    }
    
    public boolean isActive() {
        return this == PENDIENTE || this == EN_PROGRESO;
    }
    
    public static TestStatus fromDisplayName(String displayName) {
        for (TestStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado desconocido: " + displayName);
    }
}