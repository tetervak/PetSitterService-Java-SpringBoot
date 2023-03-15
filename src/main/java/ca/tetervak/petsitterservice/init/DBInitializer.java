package ca.tetervak.petsitterservice.init;

import ca.tetervak.petsitterservice.base.PetType;
import ca.tetervak.petsitterservice.base.UserType;
import ca.tetervak.petsitterservice.ents.Pet;
import ca.tetervak.petsitterservice.ents.User;
import ca.tetervak.petsitterservice.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

import static ca.tetervak.petsitterservice.util.RecordBuilder.buildUser;

/**
 * Created by iuliana.cosmina on 7/22/16.
 */
@Service
public class DBInitializer {
    private Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Autowired
    UserRepo userRepo;

    @PostConstruct
    public void init() {
        // always start with a clean database
        userRepo.deleteAll();
        logger.info("Starting database initialization...");
        Set<User> users = new HashSet<>();
        User john = buildUser("John.Cusack@pet.com");
        john.setPassword("test");
        john.setUserType(UserType.OWNER);
        users.add(john);

        Pet max = new Pet();
        max.setName("Max");
        max.setAge(10);
        max.setPetType(PetType.DOG);
        max.setRfid("1122334455");
        john.addPet(max);

        Pet mona = new Pet();
        mona.setName("Mona");
        mona.setAge(2);
        mona.setPetType(PetType.CAT);
        mona.setRfid("1100223344");
        john.addPet(mona);

        User mary = buildUser("Mary.Poppins@pet.com");
        mary.setPassword("test");
        mary.setUserType(UserType.SITTER);
        users.add(mary);

        User jessica = buildUser("Jessica.Jones@pet.com");
        jessica.setPassword("test");
        jessica.setUserType(UserType.BOTH);
        users.add(jessica);

        Pet kiki = new Pet();
        kiki.setName("Kiki");
        kiki.setAge(3);
        kiki.setPetType(PetType.BIRD);
        kiki.setRfid("1100221144");
        jessica.addPet(kiki);

        User johnny = buildUser("Johnny.Big@pet.com");
        johnny.setPassword("test");
        johnny.setUserType(UserType.SITTER);
        users.add(johnny);

        User gigi = buildUser("Gigi.Pedala@pet.com");
        gigi.setPassword("test");
        gigi.setUserType(UserType.SITTER);
        users.add(gigi);

        userRepo.saveAll(users);
        //Print them in the log
        userRepo.findAll().stream().forEach(u -> logger.debug("user" + u.toString()));
        logger.info("Database initialization finished.");
    }

    @PreDestroy
    public void destroy() {
        userRepo.deleteAll();
    }
}
