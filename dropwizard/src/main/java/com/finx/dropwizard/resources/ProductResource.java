package com.finx.dropwizard.resources;


import com.finx.dropwizard.services.ProductService;
import com.finx.dtos.ProductDto;
import com.finx.mappers.ProductMapper;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private final ProductService service;
    private final ProductMapper mapper;

    @Inject
    public ProductResource(ProductService service, ProductMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GET
    public List<ProductDto> findAll() {
        return service.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ProductDto findById(@PathParam("id") Long id) {
        return mapper.toDto(service.findById(id));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Valid ProductDto productDto) {
        return Response.status(Response.Status.CREATED)
                .entity(service.insert(mapper.toProduct(productDto)))
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public ProductDto update(@PathParam("id") long id, @Valid ProductDto productDto) {
        var updateProduct = mapper.toProduct(productDto);
        updateProduct.setId(id);
        service.update(updateProduct);
        return mapper.toDto(updateProduct);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        service.deleteById(id);
        return Response.ok().build();
    }
}


