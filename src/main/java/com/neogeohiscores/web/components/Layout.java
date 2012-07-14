package com.neogeohiscores.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Layout {

    @Inject
    private Request request;

    public boolean isProductionServer() {
        return "88.179.37.215".equals(request.getRemoteHost());
    }

}