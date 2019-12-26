package com.sippnex.landscape;

import com.sippnex.landscape.core.app.domain.App;
import com.sippnex.landscape.core.app.domain.AppSubscription;
import com.sippnex.landscape.core.app.service.AppService;
import com.sippnex.landscape.core.app.service.AppSubscriptionService;
import com.sippnex.landscape.core.security.domain.Role;
import com.sippnex.landscape.core.security.domain.User;
import com.sippnex.landscape.core.security.repository.RoleRepository;
import com.sippnex.landscape.core.security.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AppService appService;

    @Autowired
    private AppSubscriptionService appSubscriptionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() throws Exception {

        Role role = roleRepository.findByName("ROLE_A").orElseGet(() -> roleRepository.save(new Role("ROLE_A")));
        User user = userRepository.findByUsername("user1").orElseGet(() -> userRepository.save(new User("user1", "user1@test.com", passwordEncoder.encode("user1"), new Role[]{role})));
        App app = appService.save(new SampleApp("Sample App 1", "No icon"));
        AppSubscription appSubscription = appSubscriptionService.save(new AppSubscription(0, 0, 1, 1, app, user));

        mvc.perform(get("/api/core/apps")
                .header(HttpHeaders.AUTHORIZATION,
                        "Basic " + Base64Utils.encodeToString("user1:user1".getBytes()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("Sample App 1")));
    }
}
