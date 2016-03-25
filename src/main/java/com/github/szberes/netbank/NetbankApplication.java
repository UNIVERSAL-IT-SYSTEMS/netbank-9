package com.github.szberes.netbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@EnableJpaRepositories
@EnableTransactionManagement
public class NetbankApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NetbankApplication.class);
    }
//    @Override
//    public void onStartup(ServletContext container) {
//        // Create the 'root' Spring application context
//        AnnotationConfigWebApplicationContext rootContext =
//                new AnnotationConfigWebApplicationContext();
//        rootContext.register(NetbankApplication.class);
//
//        // Manage the lifecycle of the root application context
//        container.addListener(new ContextLoaderListener(rootContext));
//
//        // Create the dispatcher servlet's Spring application context
////        AnnotationConfigWebApplicationContext dispatcherContext =
////                new AnnotationConfigWebApplicationContext();
////        dispatcherContext.register(DispatcherConfig.class);
//
//        // Register and map the dispatcher servlet
//        ServletRegistration.Dynamic dispatcher =
//                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//    }

    public static void main(String[] args) {
        SpringApplication.run(NetbankApplication.class, args);
    }

//    @Autowired
//    private AccountRepository accountRepository;

//    @PostConstruct
//    private void fillWithInitialData() {
//        accountRepository.save(new AccountEntity("admin", "My first account"));
//    }
}
