package com.nequi.prueba.franquicias.presentation.handler;

import com.nequi.prueba.franquicias.domain.exception.ResourceNotFoundException;
import com.nequi.prueba.franquicias.domain.exception.UniqueViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.HashMap;

/**
 * Manejador de excepciones global para la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones de recurso no encontrado.
     *
     * @param ex la excepción de recurso no encontrado.
     * @return una respuesta con el mensaje de error y el estado de no encontrado.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja las excepciones de violación de unicidad.
     *
     * @param ex la excepción de violación de unicidad.
     * @return una respuesta con el mensaje de error y el estado de conflicto.
     */
    @ExceptionHandler(UniqueViolationException.class)
    public ResponseEntity<Map<String, String>> handleUniqueViolationException(UniqueViolationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Maneja las excepciones generales.
     *
     * @param ex la excepción general.
     * @return una respuesta con un mensaje de error genérico y el estado de error interno del servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error inesperado.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}