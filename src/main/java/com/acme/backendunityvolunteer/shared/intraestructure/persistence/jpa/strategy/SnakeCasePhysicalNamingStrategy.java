package com.acme.backendunityvolunteer.shared.intraestructure.persistence.jpa.strategy;



import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeCasePhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(this.toPlural(identifier));
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
        return this.toSnakeCase(identifier);
    }

    @Override
    public Identifier toPhysicalTypeName(Identifier logicalName, JdbcEnvironment jdbcEnvironment) {
        return PhysicalNamingStrategy.super.toPhysicalTypeName(logicalName, jdbcEnvironment);
    }

    private Identifier toSnakeCase(final Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = identifier.getText().replaceAll(regex, replacement).toLowerCase();
        return Identifier.toIdentifier(newName);
    }

    // Método para pluralizar nombres
    private Identifier toPlural(final Identifier identifier) {
        final String newName = pluralize(identifier.getText());
        return Identifier.toIdentifier(newName);
    }

    // Implementación simple del método pluralize
    private String pluralize(String name) {
        if (name.endsWith("y")) {
            return name.substring(0, name.length() - 1) + "ies";
        } else if (name.endsWith("s")) {
            return name;
        } else {
            return name + "s";
        }
    }
}
