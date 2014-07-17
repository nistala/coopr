package com.continuuity.loom.provisioner.mock;

import com.continuuity.http.AbstractHttpHandler;
import com.continuuity.http.HttpResponder;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Mock Provisioner REST APIs.
 */
@Path("/v1/tenants")
public class MockProvisionerHandler extends AbstractHttpHandler {
  private static final Logger LOG = LoggerFactory.getLogger(MockProvisionerHandler.class);
  private final MockProvisionerTenantStore provisionerTenantStore = MockProvisionerTenantStore.getInstance();
  private final Gson GSON = new Gson();

  @PUT
  @Path("/{tenant-id}")
  public void writeTenant(HttpRequest request, HttpResponder responder, @PathParam("tenant-id") String tenantId) {
    LOG.debug("Received request to put tenant {}", tenantId);
    Reader reader = new InputStreamReader(new ChannelBufferInputStream(request.getContent()), Charsets.UTF_8);
    JsonObject body = GSON.fromJson(reader, JsonObject.class);
    Integer numWorkers = body.get("workers").getAsInt();
    LOG.debug("Request to set num workers for tenant {} to {}", tenantId, numWorkers);
    if (numWorkers != provisionerTenantStore.getAssignedWorkers(tenantId)) {
      provisionerTenantStore.setAssignedWorkers(tenantId, numWorkers);
    }
    responder.sendStatus(HttpResponseStatus.OK);
  }

  @DELETE
  @Path("/{tenant-id}")
  public void deleteTenant(HttpRequest request, HttpResponder responder, @PathParam("tenant-id") String tenantId) {
    LOG.debug("Received request to delete tenant {}", tenantId);
    provisionerTenantStore.deleteTenant(tenantId);
    responder.sendStatus(HttpResponseStatus.OK);
  }
}