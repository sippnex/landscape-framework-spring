package com.sippnex.landscape;

import com.sippnex.landscape.core.LandscapeApplication;
import com.sippnex.landscape.core.app.AppRegistry;
import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.service.AppService;
import com.sippnex.landscape.core.security.domain.Role;
import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.repository.RoleRepository;
import com.sippnex.landscape.core.security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@LandscapeApplication
@EnableMongoRepositories({"com.sippnex.landscape.addressbook"})
public class Application  implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AppRegistry appRegistry;

    private final AppService appService;

    public Application(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AppRegistry appRegistry, AppService appService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appRegistry = appRegistry;
        this.appService = appService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));
        User admin = userRepository.findByUsername("admin").orElseGet(() -> userRepository.save(new User("admin", "admin@test.com", passwordEncoder.encode("admin"), new Role[]{roleAdmin})));

        Role roleA = roleRepository.findByName("ROLE_A").orElseGet(() -> roleRepository.save(new Role("ROLE_A")));
        User user1 = userRepository.findByUsername("user1").orElseGet(() -> userRepository.save(new User("user1", "user1@test.com", passwordEncoder.encode("user1"), new Role[]{roleA})));

        appRegistry.registerApp("SampleApp", SampleApp.class);
        App sampleApp = appService.getAppByName("Sample App 1").orElseGet(() -> appService.save(new SampleApp("Sample App 1", "No Icon")));

    }

}
