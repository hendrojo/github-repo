package roman.convert.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

@PropertySource("classpath:bicac_app.properties")
public class SpringWebAppInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
    	System.setProperty(LogFactory.FACTORY_PROPERTY, LogFactory.FACTORY_DEFAULT);
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(ApplicationContextConfig.class);
 
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
         
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener(appContext);
        servletContext.addListener(contextLoaderListener);
        
        // Filter.
        FilterRegistration.Dynamic fr = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
 
        fr.setInitParameter("encoding", "UTF-8");
        fr.setInitParameter("forceEncoding", "true");
        fr.addMappingForUrlPatterns(null, true, "/*");
    }
}
