package ru.javawebinar.topjava.service.jdbc;

import org.hibernate.cfg.Environment;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.Profiles.getActiveDbProfile;

@ActiveProfiles(JDBC)
//@Ignore
public class JdbcUserServiceTest extends AbstractUserServiceTest {
}