package com.dodo.domain;

import io.smallrye.common.constraint.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.DefaultValue;
import java.util.UUID;

public class Students {

    private UUID uuid = UUID.randomUUID();
    @NotBlank(message = "There is no First name")
    @Schema(required = true, example = "Syairah")
    private String firstName;
    @NotBlank(message = "There is no Last name")
    @Schema(required = true, example = "Putri")
    private String lastName;
    @Schema(required = true, example = "20")
    @Min(message = "Age cannot negative", value = 0)
    private int age;
    @DefaultValue("-")
    @Schema(required = true, example = "student")
    private String occupation;

    public UUID getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
