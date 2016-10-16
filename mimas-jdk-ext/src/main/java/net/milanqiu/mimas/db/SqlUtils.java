package net.milanqiu.mimas.db;

import java.util.Collection;

/**
 * Utilities related to SQL.
 * <p>
 * Creation Date: 2016-05-08
 * @author Milan Qiu
 */
public class SqlUtils {

    /**
     * Assembles a parameterized SQL INSERT statement with the specified table name and field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param fieldNames the field names
     * @return the assembled SQL INSERT statement
     * @throws IllegalArgumentException if there is not any field
     */
    public static String parameterizedInsert(String tableName, String... fieldNames) {
        int fieldCount = fieldNames.length;
        if (fieldCount == 0)
            throw new IllegalArgumentException("should have at least one field");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
          .append(tableName)
          .append(" (");
        for (String fieldName : fieldNames) {
            sb.append(fieldName)
              .append(", ");
        }
        sb.setCharAt(sb.length()-2, ')');
        sb.append("VALUES (");
        for (int i = 0; i < fieldCount; i++) {
            if (i == 0)
                sb.append("?");
            else
                sb.append(", ?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL INSERT statement with the specified table name and field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param fieldNames the field names
     * @return the assembled SQL INSERT statement
     * @throws IllegalArgumentException if there is not any field
     */
    public static String parameterizedInsert(String tableName, Collection<String> fieldNames) {
        int fieldCount = fieldNames.size();
        if (fieldCount == 0)
            throw new IllegalArgumentException("should have at least one field");

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
          .append(tableName)
          .append(" (");
        for (String fieldName : fieldNames) {
            sb.append(fieldName)
              .append(", ");
        }
        sb.setCharAt(sb.length()-2, ')');
        sb.append("VALUES (");
        for (int i = 0; i < fieldCount; i++) {
            if (i == 0)
                sb.append("?");
            else
                sb.append(", ?");
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL UPDATE statement with the specified table name, filter field name and updated field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param filterFieldName the filter field name
     * @param updatedFieldNames the updated field names
     * @return the assembled SQL UPDATE statement
     * @throws IllegalArgumentException if there is not any updated field
     */
    public static String parameterizedUpdate(String tableName, String filterFieldName, String... updatedFieldNames) {
        int updatedFieldCount = updatedFieldNames.length;
        if (updatedFieldCount == 0)
            throw new IllegalArgumentException("should have at least one updated field");

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
          .append(tableName)
          .append(" SET ");
        for (String updatedFieldName : updatedFieldNames) {
            sb.append(updatedFieldName)
              .append(" = ?, ");
        }
        sb.setCharAt(sb.length()-2, ' ');
        sb.setCharAt(sb.length()-1, 'W');
        sb.append("HERE ")
          .append(filterFieldName)
          .append(" = ?");
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL UPDATE statement with the specified table name, filter field name and updated field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param filterFieldName the filter field name
     * @param updatedFieldNames the updated field names
     * @return the assembled SQL UPDATE statement
     * @throws IllegalArgumentException if there is not any updated field
     */
    public static String parameterizedUpdate(String tableName, String filterFieldName, Collection<String> updatedFieldNames) {
        int updatedFieldCount = updatedFieldNames.size();
        if (updatedFieldCount == 0)
            throw new IllegalArgumentException("should have at least one updated field");

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
          .append(tableName)
          .append(" SET ");
        for (String updatedFieldName : updatedFieldNames) {
            sb.append(updatedFieldName)
              .append(" = ?, ");
        }
        sb.setCharAt(sb.length()-2, ' ');
        sb.setCharAt(sb.length()-1, 'W');
        sb.append("HERE ")
          .append(filterFieldName)
          .append(" = ?");
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL UPDATE statement with the specified table name, filter field names and updated field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param filterFieldNames the filter field names
     * @param updatedFieldNames the updated field names
     * @return the assembled SQL UPDATE statement
     * @throws IllegalArgumentException if there is not any filter field or updated field
     */
    public static String parameterizedUpdate(String tableName, Collection<String> filterFieldNames, Collection<String> updatedFieldNames) {
        int filterFieldCount = filterFieldNames.size();
        if (filterFieldCount == 0)
            throw new IllegalArgumentException("should have at least one filter field");
        int updatedFieldCount = updatedFieldNames.size();
        if (updatedFieldCount == 0)
            throw new IllegalArgumentException("should have at least one updated field");

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ")
          .append(tableName)
          .append(" SET ");
        for (String updatedFieldName : updatedFieldNames) {
            sb.append(updatedFieldName)
              .append(" = ?, ");
        }
        sb.setCharAt(sb.length()-2, ' ');
        sb.setCharAt(sb.length()-1, 'W');
        sb.append("HERE ");
        for (String filterFieldName : filterFieldNames) {
            sb.append(filterFieldName)
              .append(" = ? AND ");
        }
        sb.delete(sb.length()-" AND ".length(), sb.length());
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL DELETE statement with the specified table name and filter field name.
     * The field value is replaced by parameter placeholder '?'.
     * @param tableName the table name
     * @param filterFieldName the filter field name
     * @return the assembled SQL DELETE statement
     */
    public static String parameterizedDelete(String tableName, String filterFieldName) {
        return "DELETE FROM " + tableName + " WHERE " + filterFieldName + " = ?";
    }

    /**
     * Assembles a parameterized SQL DELETE statement with the specified table name and filter field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param filterFieldNames the filter field names
     * @return the assembled SQL DELETE statement
     * @throws IllegalArgumentException if there is not any filter field
     */
    public static String parameterizedDelete(String tableName, String... filterFieldNames) {
        int filterFieldCount = filterFieldNames.length;
        if (filterFieldCount == 0)
            throw new IllegalArgumentException("should have at least one filter field");

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
          .append(tableName)
          .append(" WHERE ");
        for (String filterFieldName : filterFieldNames) {
            sb.append(filterFieldName)
              .append(" = ? AND ");
        }
        sb.delete(sb.length()-" AND ".length(), sb.length());
        return sb.toString();
    }

    /**
     * Assembles a parameterized SQL DELETE statement with the specified table name and filter field names.
     * The field values are replaced by parameter placeholders '?'.
     * @param tableName the table name
     * @param filterFieldNames the filter field names
     * @return the assembled SQL DELETE statement
     * @throws IllegalArgumentException if there is not any filter field
     */
    public static String parameterizedDelete(String tableName, Collection<String> filterFieldNames) {
        int filterFieldCount = filterFieldNames.size();
        if (filterFieldCount == 0)
            throw new IllegalArgumentException("should have at least one filter field");

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ")
          .append(tableName)
          .append(" WHERE ");
        for (String filterFieldName : filterFieldNames) {
            sb.append(filterFieldName)
              .append(" = ? AND ");
        }
        sb.delete(sb.length()-" AND ".length(), sb.length());
        return sb.toString();
    }
}
