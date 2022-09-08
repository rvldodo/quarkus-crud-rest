package com.dodo;


import com.dodo.domain.Students;
import io.smallrye.common.constraint.NotNull;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResources {

    @Inject
    Validator validator;

    public Map<UUID,Students> students = new HashMap();

    // call all the students data
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents(){
        return Response.ok(students).build();
    }

    // create a student data
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Result saveStudents(@Valid @NotNull Students s) {
        Set<ConstraintViolation<Students>> violations = validator.validate(s);
        if(students.isEmpty()){
            students.put(s.getUuid(), s);
            return new Result("Data added");
        } else {
            return new Result(violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(", ")));
        }
    }

    // call the student from students by uuid
    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(UUID uuid) {
        Students student = students.get(uuid);
        if(Objects.isNull(student)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(student).build();
    }

    @PUT
    @Path("{uuid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(
            @PathParam("uuid") UUID uuid,
            @Valid Students s) {
        Students student = students.get(uuid);
        try{
            student.setFirstName(s.getFirstName());
            student.setLastName(s.getLastName());
            student.setAge(s.getAge());
            student.setOccupation(s.getOccupation());
            Set<ConstraintViolation<Students>> violations = validator.validate(student);
            return Response.ok(student).build();
        } catch (ConstraintViolationException e) {
            return Response.status(400, "Cannot update the data").build();
        }

    }

    // delete a student data in students by uuid
    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(UUID uuid){
        students.remove(uuid);
        return Response.ok(students).build();
    }

    /*
    * VALIDATOR */
    public static class Result{
        private String message;
        private Boolean success;

        Result (String message){
            this.success = true;
            this.message = message;
        }

        Result(Set<? extends ConstraintViolation<?>> violations){
            this.success = false;
            this.message = violations.stream().map(cv -> cv.getMessage()).collect(Collectors.joining(", "));
        }

        public String getMessage() {
            return message;
        }

        public Boolean isSuccess() {
            return success;
        }
    }
}
