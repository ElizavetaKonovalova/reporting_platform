package application.models;

import javax.persistence.*;

/**
 * Created by ekonovalova on 07-Dec-16.
 */

@Entity
@Table( indexes = {@Index(unique = true, name = "surveytypesIndex", columnList = "typename")})
public class SurveyTypes {

    @Id
    @Column(name ="id", columnDefinition = "serial", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ID;
    private String SUBTYPENAME;
    private String TYPENAME;

    /* Simple getters for the properties */
    public Long getID() { return this.ID; }
    public String getSUBTYPENAME() { return this.SUBTYPENAME; }
    public String getTYPENAME() { return this.TYPENAME; }

    /* Simple setters for the properties */
    public void setID(Long id) { this.ID = id; }
    public void setSUBTYPENAME(String subtypename) { this.SUBTYPENAME = subtypename; }
    public void setTYPENAME(String typename) { this.TYPENAME = typename; }
}
