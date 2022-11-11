package equipo1.registraduria.seguridad.Repositorios;
import equipo1.registraduria.seguridad.Modelos.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RepositorioPermiso extends MongoRepository<Permiso,String> {


}
