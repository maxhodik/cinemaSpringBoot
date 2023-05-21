package com.ua.hodik.cinema.dto;



import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

public class FilterDto<T> {
    private String column;
    private List<T> values;
    private Operation operations;
//    private final Class<T> clazz;
    public FilterDto(String column, List<T> values, Operation operations) {
        this.column = column;
        this.values = values;
        this.operations = operations;
//        this.clazz = clazz;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public Operation getOperations() {
        return operations;
    }

    public void setOperations(Operation operations) {
        this.operations = operations;
    }

//    public Class<T> getClazz() {
//        return clazz;
//    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterDto filterDto = (FilterDto) o;
        return Objects.equals(column, filterDto.column) && Objects.equals(values, filterDto.values) && operations == filterDto.operations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, values, operations);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("column", column)
                .append("values", values)
                .append("operations", operations)
                .toString();
    }
}
