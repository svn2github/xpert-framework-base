package com.base.modelo.audit;

import com.xpert.audit.model.AbstractAuditing;
import com.xpert.audit.model.AbstractMetadata;
import javax.persistence.*;

/**
 *
 * @author
 */
@Entity
public class Metadata extends AbstractMetadata{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Auditing auditing;

    @Override
    public Long getId() {
       return id;
    }

    @Override
    public AbstractAuditing getAuditing() {
        return auditing;
    }

    @Override
    public void setAuditing(AbstractAuditing auditing) {
        this.auditing = (Auditing) auditing;
    }
}
