package com.infy.jhipster5.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.infy.jhipster5.domain.User} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDTO)) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            "}";
    }
}
