package org.example.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "position")
    private String position;

    public Employee(int id, String name, String surname, String patronymic, String position) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
    }

    public Employee(String name, String surname, String patronymic, String position) {
        this.id = null;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.position = position;
    }

    public Employee() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(name, employee.name)
                && Objects.equals(surname, employee.surname)
                && Objects.equals(patronymic, employee.patronymic)
                && Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(surname);
        result = 31 * result + Objects.hashCode(patronymic);
        result = 31 * result + Objects.hashCode(position);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
