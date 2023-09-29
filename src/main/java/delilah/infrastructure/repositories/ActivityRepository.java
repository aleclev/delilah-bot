package delilah.infrastructure.repositories;

import delilah.domain.models.groupEvent.Activity;

import java.util.List;

public interface ActivityRepository extends Repository<Activity> {

    List<Activity> searchByName(String name);
}
