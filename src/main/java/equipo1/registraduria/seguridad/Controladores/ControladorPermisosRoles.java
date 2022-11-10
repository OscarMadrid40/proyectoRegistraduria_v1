package equipo1.registraduria.seguridad.Controladores;
import equipo1.registraduria.seguridad.Modelos.Permiso;
import equipo1.registraduria.seguridad.Modelos.PermisosRoles;
import equipo1.registraduria.seguridad.Modelos.Rol;

import equipo1.registraduria.seguridad.Repositorios.RepositorioRol;
import equipo1.registraduria.seguridad.Repositorios.RepositorioPermiso;
import equipo1.registraduria.seguridad.Repositorios.RepositorioPermisosRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/permisos-roles")
public class ControladorPermisosRoles {
    @Autowired
    private RepositorioPermisosRoles miRepositorioPermisoRoles;
    @Autowired
    private RepositorioPermiso miRepositorioPermiso;
    @Autowired
    private RepositorioRol miRepositorioRol;

    @GetMapping("")
    public List<PermisosRoles> index(){
        return this.miRepositorioPermisoRoles.findAll();
    }

    @GetMapping("validar-permiso/rol/{id_rol}")
    public PermisosRoles getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso){
        Permiso elPermiso = this.miRepositorioPermiso
                .getPermiso(infoPermiso.getUrl(),infoPermiso.getMetodo());
        Rol elRol = this.miRepositorioRol
                .findById(id_rol)
                .orElse(null);
        if(elPermiso != null && elRol != null){
            return this.miRepositorioPermisoRoles.getPermisoRol(elRol.get_id(),elPermiso.get_id());
        }else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("rol/{id_rol}/permisos/{id_permiso}")
    public PermisosRoles create(@PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRoles nuevo = new PermisosRoles();
        Rol elRol = this.miRepositorioRol
                .findById(id_rol).orElse(null);
        Permiso elPermiso = this.miRepositorioPermiso
                .findById(id_permiso).orElse(null);
        if(elPermiso != null && elRol != null){
            nuevo.setPermiso(elPermiso);
            nuevo.setRol(elRol);
            return this.miRepositorioPermisoRoles.save(nuevo);
        }else{
            return null;
        }
    }

    @GetMapping("{id}")
    public PermisosRoles show(@PathVariable String id){
        PermisosRoles permisosRolesActual = this.miRepositorioPermisoRoles
                .findById(id)
                .orElse(null);
        return permisosRolesActual;
    }

    @PutMapping("{id}/rol/{id_rol}/permiso/{id_permiso}")
    public PermisosRoles update(@PathVariable String id, @PathVariable String id_rol, @PathVariable String id_permiso){
        PermisosRoles permisosRolesActual = this.miRepositorioPermisoRoles
                .findById(id)
                .orElse(null);
        Rol elRol = this.miRepositorioRol
                .findById(id)
                .orElse(null);
        Permiso elPermiso = this.miRepositorioPermiso
                .findById(id)
                .orElse(null);
        if(permisosRolesActual != null && elPermiso != null && elRol != null){
            permisosRolesActual.setRol(elRol);
            permisosRolesActual.setPermiso(elPermiso);
            return this.miRepositorioPermisoRoles.save(permisosRolesActual);
        }else {
            return null;
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        PermisosRoles permisosRolesActual = this.miRepositorioPermisoRoles
                .findById(id)
                .orElse(null);
        if(permisosRolesActual != null){
            this.miRepositorioPermisoRoles.delete(permisosRolesActual);
        }
    }
}
