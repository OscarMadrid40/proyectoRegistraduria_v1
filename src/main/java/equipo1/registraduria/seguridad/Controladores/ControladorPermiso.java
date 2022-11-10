package equipo1.registraduria.seguridad.Controladores;
import equipo1.registraduria.seguridad.Modelos.Permiso;
import equipo1.registraduria.seguridad.Repositorios.RepositorioPermiso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
@CrossOrigin
@RestController
@RequestMapping("/permisos")
public class ControladorPermiso {

    @Autowired
    private RepositorioPermiso miRepositorioPermiso;

    @GetMapping("")
    public List<Permiso> index(){
        return this.miRepositorioPermiso.findAll();
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Permiso create(@RequestBody Permiso infoPermiso){
        System.out.println("Creando un Permiso");
        return this.miRepositorioPermiso.save(infoPermiso);
    }
    @GetMapping("{id}")
    public Permiso show(@PathVariable String id){
        System.out.println("Mostrando un Permiso con id "+ id);
        Permiso permisoActual = this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        return permisoActual;
    }
    @PutMapping("{id}")
    public Permiso update(@PathVariable String id, @RequestBody Permiso infoPermiso) {
        System.out.println("Actualizando un Permiso con id "+ id);
        Permiso permisoActual = this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if(permisoActual != null) {
            permisoActual.setMetodo(infoPermiso.getMetodo());
            permisoActual.setUrl(infoPermiso.getUrl());
            return this.miRepositorioPermiso.save(permisoActual);
        } else {
            return null;
        }
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        System.out.println("Eliminando un Permiso con id "+ id);
        Permiso permisoAEliminar = this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if(permisoAEliminar != null){
            this.miRepositorioPermiso.delete(permisoAEliminar);
        }
    }
}
