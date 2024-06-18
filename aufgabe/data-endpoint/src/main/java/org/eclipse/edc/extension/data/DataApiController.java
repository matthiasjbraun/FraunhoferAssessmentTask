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
import com.google.gson.reflect.TypeToken;
import database.DataManager;
import database.Person;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;

import java.util.List;

@Path("/")
public class DataApiController {

    private final Monitor monitor;
    private final DataManager dataManager;

    public DataApiController(Monitor monitor) {
        this.monitor = monitor;
        this.dataManager = new DataManager();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("data")
    public String checkStatus() {
        monitor.info("Received a Status request");
        return "working!";
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("data/person")
    public String findPerson(@QueryParam("id") String id, @QueryParam("name") String name,
                             @QueryParam("username") String username) {
        monitor.info("Received a GET Query request");
        Gson gson = new Gson();
        List<Person> personList = dataManager.findPersonWithAll(id, name, username);
        return gson.toJson(personList);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("data")
    public String persistData(String json)  {
        monitor.info("Received a POST request");
        Gson gson = new Gson();
        List<Person> personList = gson.fromJson(json, new TypeToken<List<Person>>(){}.getType());
        dataManager.persistPerson(personList);
        return "Received Request" + personList.get(0).getName() + personList.get(1).getName();
    }
}
