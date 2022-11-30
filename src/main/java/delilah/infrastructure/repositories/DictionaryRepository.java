package delilah.infrastructure.repositories;

import delilah.domain.models.dictionnary.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, String> {
}
