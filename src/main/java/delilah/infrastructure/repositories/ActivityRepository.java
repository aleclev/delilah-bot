package delilah.infrastructure.repositories;

import delilah.domain.models.lookingForGroup.Activity;

import java.util.List;

public interface ActivityRepository extends Repository<Activity> {

    List<Activity> searchByName(String name);
}
