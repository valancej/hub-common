package com.blackducksoftware.integration.hub.api.component;

import static com.blackducksoftware.integration.hub.api.UrlConstants.SEGMENT_API;
import static com.blackducksoftware.integration.hub.api.UrlConstants.SEGMENT_COMPONENTS;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.restlet.data.Method;

import com.blackducksoftware.integration.hub.api.HubItemRestService;
import com.blackducksoftware.integration.hub.api.HubRequest;
import com.blackducksoftware.integration.hub.exception.BDRestException;
import com.blackducksoftware.integration.hub.rest.RestConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class ComponentRestService extends HubItemRestService<ComponentItem> {
    private static final List<String> COMPONENT_SEGMENTS = Arrays.asList(SEGMENT_API, SEGMENT_COMPONENTS);

    private static Type ITEM_TYPE = new TypeToken<ComponentItem>() {
    }.getType();

    private static Type ITEM_LIST_TYPE = new TypeToken<List<ComponentItem>>() {
    }.getType();

    public ComponentRestService(final RestConnection restConnection, final Gson gson, final JsonParser jsonParser) {
        super(restConnection, gson, jsonParser, ITEM_TYPE, ITEM_LIST_TYPE);
    }

    public List<ComponentItem> getAllComponents(final String id, final String groupId, final String artifactId,
            final String version) throws IOException, BDRestException, URISyntaxException {
        final HubRequest componentItemRequest = new HubRequest(getRestConnection(), getJsonParser());
        final ComponentQuery componentQuery = new ComponentQuery(id, groupId, artifactId, version);

        componentItemRequest.setMethod(Method.GET);
        componentItemRequest.setLimit(5);
        componentItemRequest.addUrlSegments(COMPONENT_SEGMENTS);
        componentItemRequest.setQ(componentQuery.getQuery());

        final JsonObject jsonObject = componentItemRequest.executeForResponseJson();
        final List<ComponentItem> allComponents = getAll(jsonObject, componentItemRequest);
        return allComponents;
    }

}