package com.anglesharp.app.resources;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import com.anglesharp.app.AppConfiguration;
import com.anglesharp.app.auth.SessionUser;
import com.anglesharp.app.core.User;
import com.anglesharp.app.db.DB;
import com.anglesharp.app.email.EmailService;
import com.anglesharp.app.roles.Roles;
import com.anglesharp.app.roles.Roles.Role;
import com.anglesharp.app.util.PasswordHash;
import com.anglesharp.app.util.UUIDUtil;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class Main extends Resource {
  
  private final DB db;
  private final AppConfiguration config;
  
  @Inject
  public Main(DB db, AppConfiguration config) {
    this.db = db;
    this.config = config;
  }
  
  @Path("login")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @UnitOfWork
  public Response login(@Context HttpServletRequest request, User user) {
    
    User foundUser = db.users().findByEmail(user.getEmail());
    
    if(foundUser == null || !foundUser.authenticate(user.getPassword()))
      return Response.status(Status.UNAUTHORIZED).build();
    
    foundUser.setLastLogin(new Date());
    db.users().save(foundUser);
    
    request.getSession().setAttribute("user", foundUser);
    
    return Response.ok("{}").build();
  }
  
  @Path("login/validate")
  @GET
  @UnitOfWork
  public Response validateSession(@SessionUser User user) {
    if(user == null)
      return Response.status(Status.UNAUTHORIZED).entity("{ \"valid\" : \"false\"}").build();
    
    return Response.ok("{ \"valid\" : \"true\"}").build();
  }
  
  @Path("/logout")
  @POST
  @UnitOfWork
  public Response logout(@SessionUser User user, @Context HttpServletRequest request) {
    
    request.getSession().invalidate();
    
    return Response.ok().build();
  }
  
  @Path("users/recover/password")
  @POST
  @Consumes(MediaType.WILDCARD)
  @UnitOfWork
  public Response recoverPassword(@QueryParam(value="email") String email) {
    
    if(StringUtils.isBlank(email))
      return Response.status(Status.BAD_REQUEST).entity("email is required").build();
    
    User u = db.users().findByEmail(email);
    
    if(u == null)
      return Response.ok().build();
    
    u.setPasswordResetCode(UUIDUtil.code());
    db.users().save(u);
    
    EmailService emailService = new EmailService(config, db.emails());
    
    Map<String, Object> renderArgs = Maps.newHashMap();
    renderArgs.put("passwordResetLink", config.getRootUrl() + "#recover/password/" + u.getPasswordResetCode());
    
    emailService.send("passwordRecovery.mustache", renderArgs, u.getEmail(), "Your [app] password reset request");
    
    return Response.ok().build();
  }
  
  @Path("users")
  @DELETE
  @Consumes(MediaType.WILDCARD)
  @UnitOfWork
  public Response resetPassword(@SessionUser User user) {
    
    user.setDeleted(new Date());
    db.users().save(user);
    
    return Response.ok().build();
  }
  
  @Path("users/recover/password/{code}")
  @POST
  @Consumes(MediaType.WILDCARD)
  @UnitOfWork
  public Response resetPassword(@PathParam(value = "code") String code, @QueryParam(value = "password") String password) {
    
    List<User> userList = db.users().findBy("passwordResetCode", code);
    
    if(userList.isEmpty() || userList.size() > 1)
      return Response.status(Status.BAD_REQUEST).entity("Invalid code").build();
    
    User u = userList.get(0);
    u.setPassword(PasswordHash.hash(password));
    db.users().save(u);
    
    return Response.ok().build();
  }
  
  @Path("users/email/validate/{code}")
  @POST
  @Consumes(MediaType.WILDCARD)
  @UnitOfWork
  public Response validateEmail(@PathParam(value = "code") String code) {
    
    List<User> userList = db.users().findBy("emailVerificationCode", code);
    
    if(userList.isEmpty() || userList.size() > 1)
      return Response.status(Status.BAD_REQUEST).entity("Invalid code").build();
    
    User u = userList.get(0);
    u.setEmailVerified(true);
    db.users().save(u);
    
    return Response.ok().build();
  }
  
  @Path("users")
  @POST
  @UnitOfWork
  public Response createAccount(User user) {
    
    List<User> users = db.users().findBy("email", user.getEmail());
    
    if(!users.isEmpty())
      return Response.status(Status.NOT_MODIFIED).build();
    
    if(StringUtils.isBlank(user.getEmail()))
      return Response.status(Status.BAD_REQUEST).build();
    
    if(StringUtils.isBlank(user.getPassword()))
      return Response.status(Status.BAD_REQUEST).build();
    
    User u = new User(user.getEmail(), user.getPassword());
    
    Role r = Role.valueOf(user.getRoleName());
    if(r == null)
      return Response.status(Status.BAD_REQUEST).build();
    
    Roles roles = db.roles().find(r);
    
    if(roles == null)
      return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    
    db.users().create(u);
    
    EmailService emailService = new EmailService(config, db.emails());
    emailService.send("welcome.beta.mustache", null, u.getEmail(), "Welcome to [app]");
    
    return Response.ok().build();
  }
}
