package site.clzblog.application.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import site.clzblog.application.mapper.UserMapper;

@Slf4j
@Component
public class ApplicationInitializer implements ApplicationRunner {
    private final UserMapper userMapper;

    public ApplicationInitializer(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("{}", userMapper.selectById(1L));
    }
}
