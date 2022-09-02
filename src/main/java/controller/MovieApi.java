package controller;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import model.Movie;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static model.Movie.*;

@Path("/api/movies")
@RequestScoped
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MovieApi {
    @Operation(
            summary = "List movies",
            description = "List movies."
    )
    @GET
    @Transactional
    public List<Movie> list() {
        return listAll();
    }

    @Operation(
            summary = "Add movies",
            description = "Add movies with system-generated `code`."
    )
    @POST
    @Transactional
    public List<Movie> add(@RequestBody(required = true) List<Movie> passed) {
        return passed.stream().map(p -> persist(p, new Movie())).collect(toUnmodifiableList());
    }

    @Operation(
            summary = "Get a movie detail",
            description = "Get a movie detail."
    )
    @Path("/{code}")
    @GET
    @Transactional
    public Movie get(@Parameter(required = true) @PathParam("code") String code) {
        return getByCode(code);
    }

    @Operation(
            summary = "Update movies",
            description = "Update movies, overwriting all attributes except `code`."
    )
    @PUT
    @Transactional
    public List<Movie> update(@RequestBody(required = true) List<Movie> passed) {
        return listByCodes(passed).stream()
                .peek(stored -> persist(getWithCode(passed, stored.getCode()), stored))
                .collect(toUnmodifiableList());
    }

    @Operation(
            summary = "Delete movies",
            description = "Delete movies."
    )
    @DELETE
    @Transactional
    public void delete(@RequestBody(required = true) List<Movie> passed) {
        listByCodes(passed).forEach(PanacheEntityBase::delete);
    }

    private Movie persist(Movie from, Movie to) {
        to.setCode(from.getCode());

        to.setTitle(from.getTitle());
        to.setDescription(from.getDescription());
        to.persist();

        return to;
    }
}
