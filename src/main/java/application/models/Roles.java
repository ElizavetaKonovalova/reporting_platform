package application.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "roles", indexes = {@Index(name = "rolesIndex", columnList = "role_name", unique = true)})
public class Roles {

    @Id
    @Column(name ="db_id", columnDefinition = "SERIAL", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ROLE_ID;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    private String ROLE_NAME;

    @Column(nullable = false)
    private Date DATE_MODIFIED;

    /* Possible levels: 1 - 5 */
    @Column(nullable = false)
    private Short PERMISSION_LEVEL;

    /* Simple getters */
    public String getROLE_NAME() { return this.ROLE_NAME; }
    public Short getPERMISSION_LEVEL() { return this.PERMISSION_LEVEL; }
    public Integer getROLE_ID() { return this.ROLE_ID; }
    public Date getDATE_MODIFIED() { return this.DATE_MODIFIED; }

    /* Simple setters */
    public void setROLE_NAME(String role_name) { this.ROLE_NAME = role_name; }
    public void setROLE_ID(Integer role_id) { this.ROLE_ID = role_id; }
    public void setPERMISSION_LEVEL(Short permission_level) { this.PERMISSION_LEVEL = permission_level; }
    public void setDATE_MODIFIED(Date date_modified) { this.DATE_MODIFIED = date_modified; }
}
