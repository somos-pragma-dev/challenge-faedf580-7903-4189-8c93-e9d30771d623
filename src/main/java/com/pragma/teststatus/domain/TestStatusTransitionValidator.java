package com.pragma.teststatus.domain;

import java.util.*;
import java.util.stream.Collectors;

public class TestStatusTransitionValidator {
    
    private static final Map<TestStatus, Set<TestStatus>> ALLOWED_TRANSITIONS = new HashMap<>();
    
    static {
        ALLOWED_TRANSITIONS.put(TestStatus.PENDIENTE, 
            EnumSet.of(TestStatus.EN_PROGRESO, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.EN_PROGRESO, 
            EnumSet.of(TestStatus.FALLIDA, TestStatus.EXITOSA, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.FALLIDA, 
            EnumSet.of(TestStatus.EN_PROGRESO, TestStatus.CANCELADA));
        
        ALLOWED_TRANSITIONS.put(TestStatus.EXITOSA, 
            EnumSet.noneOf(TestStatus.class));
        
        ALLOWED_TRANSITIONS.put(TestStatus.CANCELADA, 
            EnumSet.noneOf(TestStatus.class));
    }
    
    public void validate(TestStatus currentStatus, TestStatus newStatus) {
        if (currentStatus == null) {
            throw new IllegalStateException("El estado actual de la prueba no puede ser null");
        }
        
        if (newStatus == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser null");
        }
        
        if (currentStatus == newStatus) {
            throw new IllegalStateException("La prueba ya se encuentra en el estado: " + newStatus.getDisplayName());
        }
        
        Set<TestStatus> allowedTransitions = ALLOWED_TRANSITIONS.get(currentStatus);
        
        if (allowedTransitions == null || !allowedTransitions.contains(newStatus)) {
            throw new InvalidStatusTransitionException(
                buildErrorMessage(currentStatus, newStatus, allowedTransitions)
            );
        }
    }
    
    public boolean isValidTransition(TestStatus currentStatus, TestStatus newStatus) {
        if (currentStatus == null || newStatus == null) {
            return false;
        }
        
        Set<TestStatus> allowedTransitions = ALLOWED_TRANSITIONS.get(currentStatus);
        return allowedTransitions != null && allowedTransitions.contains(newStatus);
    }
    
    public Set<TestStatus> getAllowedTransitionsFrom(TestStatus currentStatus) {
        if (currentStatus == null) {
            return EnumSet.noneOf(TestStatus.class);
        }
        
        Set<TestStatus> transitions = ALLOWED_TRANSITIONS.get(currentStatus);
        return transitions != null ? EnumSet.copyOf(transitions) : EnumSet.noneOf(TestStatus.class);
    }
    
    public Map<TestStatus, List<TestStatus>> getAllValidTransitions() {
        return ALLOWED_TRANSITIONS.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new ArrayList<>(e.getValue())
            ));
    }
    
    private String buildErrorMessage(TestStatus currentStatus, TestStatus newStatus, 
                                     Set<TestStatus> allowedTransitions) {
        StringBuilder message = new StringBuilder();
        message.append("Transición de estado inválida: no se puede cambiar de '")
               .append(currentStatus.getDisplayName())
               .append("' a '")
               .append(newStatus.getDisplayName())
               .append("'. ");
        
        if (allowedTransitions == null || allowedTransitions.isEmpty()) {
            message.append("El estado '")
                   .append(currentStatus.getDisplayName())
                   .append("' es un estado terminal.");
        } else {
            message.append("Los estados permitidos son: ");
            String allowedList = allowedTransitions.stream()
                .map(TestStatus::getDisplayName)
                .collect(Collectors.joining(", "));
            message.append(allowedList);
            message.append(".");
        }
        
        return message.toString();
    }
    
    public static class InvalidStatusTransitionException extends RuntimeException {
        private final TestStatus fromStatus;
        private final TestStatus toStatus;
        
        public InvalidStatusTransitionException(String message) {
            super(message);
            this.fromStatus = null;
            this.toStatus = null;
        }
        
        public InvalidStatusTransitionException(String message, TestStatus fromStatus, TestStatus toStatus) {
            super(message);
            this.fromStatus = fromStatus;
            this.toStatus = toStatus;
        }
        
        public TestStatus getFromStatus() {
            return fromStatus;
        }
        
        public TestStatus getToStatus() {
            return toStatus;
        }
    }
}