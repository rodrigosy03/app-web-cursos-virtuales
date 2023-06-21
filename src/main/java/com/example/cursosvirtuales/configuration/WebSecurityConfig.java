package com.example.cursosvirtuales.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**", "/", "/logeo", "/login")
                .permitAll()
                //Estudiante
                .antMatchers("/estudiantes/listar").hasAnyRole("ADMIN","LECTOR","CREADOR","EDITOR","DEPURADOR")
                .antMatchers("/estudiantes/agregar").hasAnyRole("ADMIN","CREADOR")
                .antMatchers("/estudiantes/guardar").hasAnyRole("ADMIN","CREADOR","EDITOR")
                .antMatchers("/estudiantes/editar/**").hasAnyRole("ADMIN","EDITOR")
                .antMatchers("/estudiantes/eliminar/**").hasAnyRole("ADMIN","DEPURADOR")
                //Profesor
                .antMatchers("/profesores/listar").hasAnyRole("ADMIN","LECTOR","CREADOR","EDITOR","DEPURADOR")
                .antMatchers("/profesores/agregar").hasAnyRole("ADMIN","CREADOR")
                .antMatchers("/profesores/guardar").hasAnyRole("ADMIN","CREADOR","EDITOR")
                .antMatchers("/profesores/editar/**").hasAnyRole("ADMIN","EDITOR")
                .antMatchers("/profesores/eliminar/**").hasAnyRole("ADMIN","DEPURADOR")
                //Inscripcion
                .antMatchers("/inscripciones/listar").hasAnyRole("ADMIN","LECTOR","CREADOR","EDITOR","DEPURADOR")
                .antMatchers("/inscripciones/agregar").hasAnyRole("ADMIN","CREADOR")
                .antMatchers("/inscripciones/guardar").hasAnyRole("ADMIN","CREADOR","EDITOR")
                .antMatchers("/inscripciones/editar/**").hasAnyRole("ADMIN","EDITOR")
                .antMatchers("/inscripciones/eliminar/**").hasAnyRole("ADMIN","DEPURADOR")
                //Curso
                .antMatchers("/cursos/listar").hasAnyRole("ADMIN","LECTOR","CREADOR","EDITOR","DEPURADOR")
                .antMatchers("/cursos/agregar").hasAnyRole("ADMIN","CREADOR")
                .antMatchers("/cursos/guardar").hasAnyRole("ADMIN","CREADOR","EDITOR")
                .antMatchers("/cursos/editar/**").hasAnyRole("ADMIN","EDITOR")
                .antMatchers("/cursos/eliminar/**").hasAnyRole("ADMIN","DEPURADOR")
                //Calificicacion
                .antMatchers("/calificaciones/listar").hasAnyRole("ADMIN","LECTOR","CREADOR","EDITOR","DEPURADOR")
                .antMatchers("/calificaciones/agregar").hasAnyRole("ADMIN","CREADOR")
                .antMatchers("/calificaciones/guardar").hasAnyRole("ADMIN","CREADOR","EDITOR")
                .antMatchers("/calificaciones/editar/**").hasAnyRole("ADMIN","EDITOR")
                .antMatchers("/calificaciones/eliminar/**").hasAnyRole("ADMIN","DEPURADOR")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll()
                .and().logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.sendRedirect("/custom-error"); // Redirigir a la nueva ruta del controlador personalizado
                });
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication().withUser("rodrigo").password(encoder.encode("rodrigo")).roles("ADMIN").and()
                .withUser("piero").password(encoder.encode("piero")).roles("LECTOR").and()
                .withUser("elio").password(encoder.encode("elio")).roles("CREADOR", "LECTOR").and()
                .withUser("sabrina").password(encoder.encode("sabrina")).roles("LECTOR","DEPURADOR").and()
                .withUser("eddy").password(encoder.encode("eddy")).roles("EDITOR","LECTOR").and();
    }
}
