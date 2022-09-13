package com.mindhub.marketplace.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //sobreescribe su init, solo accede desde paquete configure por el protected


        http.authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/clients").permitAll() // Con HttpMethod.POST le damos permiso solo a metodo POST para acceder a la ruta, por eso lo agregamos
                .antMatchers(HttpMethod.POST, "/api/addItemToCart").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/pay").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/shoppingCarts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/products").permitAll()
                .antMatchers(HttpMethod.GET, "/api/purchaseOrders").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/current").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/clients/update/{id}").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PATCH,"/api/clients/delete/{id}").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").hasAuthority("ADMIN");



        //si se recibe una petición con la URL /data, se verifica primero contra el antMatcher(“/admin/**”) y si este no se cumple entonces pasa al
        // siguiente, por lo que se valida contra antMatcher(“/**”) y como se cumple entonces Spring Security verificará que exista una sesión iniciada y que ese usuario tenga el rol USER para poder acceder a /data.

        http.formLogin().usernameParameter("email")  //establece lo que necesita el formulario para poder loguearse: mails, password y pagina
                .passwordParameter("password")
                .loginPage("/api/login"); // endpoint

        http.logout().logoutUrl("/api/logout"); //indica la ruta url que desloguea la sesion, borra la cookie automaticamente

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable(); //deshabilita opciones que inhiben la visual de la pagina del h2 console


        http.csrf().disable(); // proteccion activada por defecto para MVC, por eso la deshabilitamos para que no pregunte constantemente por el token

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)); // manda un mensaje de error si el usuario no esta autenticado

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req)); //es para que no vuelva a preguntar si nos logueamos, una vez que ya lo estamos. Llama al método de abajo.

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED)); // si el loguin falla, mandar un mensaje de unauthorized

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler()); // envia un mensaje de que salió bien el logout
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {  // es para eliminar las marcas que Spring establece cuando un usuario no autenticado intenta acceder a algún recurso, y así no la pide más.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); //si la session existe, remover los mensajes de error, lo llama arriba al método.
        }
    }
}


