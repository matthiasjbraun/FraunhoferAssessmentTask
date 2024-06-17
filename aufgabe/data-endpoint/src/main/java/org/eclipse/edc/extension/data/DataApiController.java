/*
 *  Copyright (c) 2021 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - Initial implementation
 *
 */

package org.eclipse.edc.extension.data;

import com.google.gson.Gson;
import database.Person;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;

@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Path("/")
public class DataApiController {

    private final Monitor monitor;

    public DataApiController(Monitor monitor) {
        this.monitor = monitor;
    }

    @GET
    @Path("data")
    public String checkHealth() {
        monitor.info("Received a request");
        return "working!";
    }

    @GET
    @Path("data/person/{id}")
    public String findPerson(@PathParam("id") String id) {
        monitor.info("Received a request");
        return id;
    }

    @POST
    @Path("data")
    public String readData(String json)  {
        Gson gson = new Gson();
        Person p = gson.fromJson(json, Person.class);
        return "got the message?" + p.getName() + p.getAddress().getCity();
    }
}
