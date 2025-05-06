package jm.task.core.jdbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;


    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", lastName= " + lastName + ", age=" + age + "]";

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
}
