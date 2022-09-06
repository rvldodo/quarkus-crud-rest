package com.dodo;


import com.dodo.domain.Students;
import io.smallrye.common.constraint.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResources {

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
    public Response saveStudents(@NotNull Students s) {
        students.put(s.getUuid(), s);
        return Response.ok(students).build();
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
            Students s) {
        Students student = students.get(uuid);
        student.setFirstName(s.getFirstName());
        student.setLastName(s.getLastName());
        student.setAge(s.getAge());
        student.setOccupation(s.getOccupation());
        return Response.ok(student).build();
    }

    // delete a student data in students by uuid
    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(UUID uuid){
        students.remove(uuid);
        return Response.ok(students).build();
    }
}
