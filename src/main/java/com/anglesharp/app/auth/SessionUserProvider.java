package com.anglesharp.app.auth;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.anglesharp.app.core.User;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

@Provider
public class SessionUserProvider implements Injectable<User>, InjectableProvider<SessionUser, Type> {

    private final HttpServletRequest request;

    public SessionUserProvider(@Context HttpServletRequest request) {
        this.request = request;
    }
    
    public Injectable<User> getInjectable(ComponentContext cc, SessionUser a, Type c) {
        if (c.equals(User.class)) {
            return this;
        }
        return null;
    }

    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    public User getValue() {
        final User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return user;
    }

}