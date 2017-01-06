package application.controllers;

import application.models.Roles;
import application.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;

    /* CREATORS */

    @RequestMapping(value = {"create", "new"}, produces = "application/json")
    public String create(@RequestParam("name") String role_name, @RequestParam(value = "plvl", required = false) String permission_level)
            throws Exception {
        return this.roleRepository.create(role_name, permission_level);

    }

    /* GETTERS */

    @RequestMapping(value = {"get", "find"}, produces = "application/json")
    public List<Roles> get(@RequestParam(value = "find", required = false) String find,
                           @RequestParam(value = "target", required = false) String target) throws Exception {

        target = (target == null) ? "": target;

        switch (target) {

            /* Select a Role by its Name */
            case "name": return this.roleRepository.getRolesByRoleName(find);

            /* Select a Role by its Modification Date */
            case "date": return this.roleRepository.getRolesByDate(find);

            /* Select a Role by its Permission Level */
            case "plvl": return this.roleRepository.getRolesByPermissionLevel(find);

            /* Select a Role by its database ID */
            case "dbid": return this.roleRepository.getRolesByRoleID(find);

            /* Select All existing Roles by default */
            default: return this.roleRepository.getAll();
        }
    }

    /* UPDATERS */

    @RequestMapping(value = "update", produces = "application/json")
    public void update(@RequestParam(value = "find") String find, @RequestParam("new") String parameter,
                           @RequestParam(value = "target") String target) throws Exception {

        switch (target) {

            /* Select a Role Name by its Name */
            case "rnamebyrname": this.roleRepository.updateRoleNameByName(find, parameter); break;

            /* Update a Permission Level by its Role Name */
            case "lvlbyrname": this.roleRepository.updatePermissionLevelByRoleName(find, parameter); break;

            /* Select a Permission Level by its database ID */
            case "lvlbyid": this.roleRepository.updatePermissionLevelByDBID(find, parameter); break;

            /* Select a Role Name by its database ID */
            case "rnamebyid": this.roleRepository.updateRoleNameByDBID(find, parameter); break;
            default: break;
        }
    }

    /* REMOVALS */

    @RequestMapping(value = {"delete", "remove"}, produces = "application/json")
    public void remove(@RequestParam(value = "find") String find, @RequestParam(value = "target") String target) throws Exception {

        switch (target) {

            /* Remove a Role by its database ID */
            case "dbid": this.roleRepository.removeRoleByDBID(find); break;

            /* Remove a Role by its Name */
            case "name": this.roleRepository.removeRoleByName(find); break;

            /* Remove a Role by its Permission Level */
            case "lvlbyid": this.roleRepository.removeRoleByPermissionLvl(find); break;
            default: break;
        }
    }
}
