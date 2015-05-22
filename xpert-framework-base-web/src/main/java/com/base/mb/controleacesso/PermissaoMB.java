package com.base.mb.controleacesso;

import com.base.bo.controleacesso.PermissaoBO;
import com.base.modelo.controleacesso.Permissao;
import java.io.Serializable;
import com.xpert.core.crud.AbstractBaseBean;
import com.xpert.core.crud.AbstractBusinessObject;
import com.xpert.faces.utils.FacesMessageUtils;
import com.xpert.faces.utils.FacesUtils;
import com.xpert.i18n.XpertResourceBundle;
import com.xpert.persistence.exception.DeleteException;
import com.xpert.persistence.query.Restriction;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Ayslan
 */
@ManagedBean
@ViewScoped
public class PermissaoMB extends AbstractBaseBean<Permissao> implements Serializable {

    @EJB
    private PermissaoBO permissaoBO;
    private boolean emCascata;

    @Override
    public AbstractBusinessObject getBO() {
        return permissaoBO;
    }

    @Override
    public String getDataModelOrder() {
        return "descricao";
    }

    @Override
    public List<Restriction> getDataModelRestrictions() {
        return null;
    }

    public void ativar() {
        permissaoBO.ativar(getEntity(), emCascata);
        getPerfilMB().carregarTree();
        FacesMessageUtils.sucess();
        emCascata = false;
    }

    public void inativar() {
        permissaoBO.inativar(getEntity(), emCascata);
        getPerfilMB().carregarTree();
        FacesMessageUtils.sucess();
        emCascata = false;
    }

    public void deleteArvore() {
        try {
            Object id = getId();
            if (getId() != null) {
                getBO().delete(id);
                FacesMessageUtils.sucess();
                id = null;
                //recarregar tree
                getPerfilMB().carregarTree();
            }
        } catch (DeleteException ex) {
            FacesMessageUtils.error(XpertResourceBundle.get("objectCannotBeDeleted"));
        }
    }

    public PerfilMB getPerfilMB() {
        return FacesUtils.getBeanByEl("#{perfilMB}");
    }

    public boolean isEmCascata() {
        return emCascata;
    }

    public void setEmCascata(boolean emCascata) {
        this.emCascata = emCascata;
    }
    
    
}
