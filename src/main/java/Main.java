import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.speedment.jpastreamer.application.JPAStreamer;
import commands.PingCommand;
import commands.registration.RegisterDiscordCommand;
import factories.UserFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.spi.PersistenceUnitInfo;
import models.dictionnary.Dictionary;
import models.user.User;
import net.dv8tion.jda.api.JDABuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;
import org.hibernate.service.ServiceRegistry;
import repositories.hibernate.UserHibernateRepository;

import javax.security.auth.login.LoginException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) throws LoginException {

        hibernateSetup();

        Clock clock = Clock.systemUTC();
        Random random = new Random(clock.instant().getEpochSecond());

        UserHibernateRepository userRepository = new UserHibernateRepository(sessionFactory);
        UserFactory userFactory = new UserFactory();

        EventWaiter waiter = new EventWaiter();

        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame();
        client.setOwnerId(System.getenv("DISCORD_ID"));
        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");
        client.setPrefix(System.getenv("DISCORD_PREFIX"));

        client.addCommands(
                new PingCommand(random),
                new RegisterDiscordCommand(userRepository, userFactory)
        );

        JDABuilder.createDefault(System.getenv("DISCORD_TOKEN"))
        .addEventListeners(waiter, client.build())
        .build();
    }

    public static void hibernateSetup() {

        Configuration configuration = new Configuration();

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, System.getenv("DB_CONNECTION_URL"));
        settings.put(Environment.USER, System.getenv("DB_USERNAME"));
        settings.put(Environment.PASS, System.getenv("DB_PASSWORD"));
        settings.put(Environment.SHOW_SQL, "true");
        settings.put(Environment.HBM2DDL_AUTO, "update");
        //settings.put(Environment.PERSISTENCE_UNIT_NAME, "delilah");
        //settings.put(Environment.JAKARTA_PERSISTENCE_PROVIDER, HibernatePersistenceProvider.class.getName());

        configuration.setProperties(settings);

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Dictionary.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
}

