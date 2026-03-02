package com.pruebasegurosbolivar.aseguradora_api.domain.model.entity;

/**
 * Enumeración para limitar los tipos de parentesco permitidos en el sistema.
 * Utilizado para validar la relación entre beneficiarios y titulares en pólizas de Salud y Vida.
 */
public enum RelationshipType {
    PADRE,
    MADRE,
    HIJO,
    HIJA,
    ESPOSA,
    ESPOSO
}