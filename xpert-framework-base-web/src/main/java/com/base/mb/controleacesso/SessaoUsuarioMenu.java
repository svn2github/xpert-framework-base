package com.base.mb.controleacesso;

import com.base.dao.controleacesso.PermissaoDAO;
import com.base.modelo.controleacesso.Permissao;
import com.xpert.i18n.I18N;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

/**
 *
 * Classe para montar o menu dinamico a partir das permissoes
 *
 * @author ayslan
 */
@Stateless
public class SessaoUsuarioMenu {

    @EJB
    private PermissaoDAO permissaoDAO;

    public MenuModel criarMenu(List<Permissao> permissoes) {

        DefaultMenuModel menuModel = new DefaultMenuModel();
        //home
        menuModel.addElement(getMenuHome());

        //urls dinamicas
        if (permissoes != null && !permissoes.isEmpty()) {
            //map para evitar a duplicidade e ter acesso mais rapido as menus
            Map<Permissao, DefaultSubMenu> subMenuMap = new HashMap<Permissao, DefaultSubMenu>();
            Map<Permissao, DefaultMenuItem> itemMenuMap = new HashMap<Permissao, DefaultMenuItem>();
            //primeiro "for" para adicionar os submenus
            for (Permissao permissao : permissoes) {
                putSubmenu(permissao, subMenuMap, menuModel);
            }

            //montar itens
            for (Permissao permissao : permissoes) {
                if (permissao.isPossuiMenu() && permissao.isAtivo()) {
                    putItemMenu(permissao, subMenuMap, itemMenuMap, menuModel);
                }
            }
        }

        //sair
        menuModel.addElement(getMenuSair());
        return menuModel;
    }

    public DefaultMenuItem getMenuHome() {
        DefaultMenuItem item = new DefaultMenuItem();
        item.setValue(I18N.get("menu.home"));
        item.setIcon("ui-icon-home");
        item.setUrl("/view/home.jsf");
        return item;
    }

    public DefaultMenuItem getMenuSair() {
        DefaultMenuItem item = new DefaultMenuItem();
        item.setValue(I18N.get("menu.sair"));
        item.setIcon("ui-icon-close");
        item.setCommand("#{loginMB.logout}");
        return item;
    }

    public void putItemMenu(Permissao permissao, Map<Permissao, DefaultSubMenu> subMenuMap, Map<Permissao, DefaultMenuItem> itemMenuMap, MenuModel menuModel) {
        //se ja estiver adicionado nao adcionar novamente
        if (itemMenuMap.containsKey(permissao)) {
            return;
        }
        String url = permissao.getUrlMenuVerificado();
        if (url != null && !url.trim().isEmpty()) {
            Submenu submenu = null;
            Permissao permissaoPai = permissaoDAO.getInitialized(permissao.getPermissaoPai());
            if (permissaoPai != null) {
                submenu = subMenuMap.get(permissaoPai);
                //cadicionar o menu pai quando não encontrado
                if (submenu == null) {
                    putSubmenu(permissaoPai, subMenuMap, menuModel);
                    submenu = subMenuMap.get(permissaoPai);
                }
            }
            //se o menu pai for nulo ou se encontrou o menu pai e ele possui menu pai
            if (permissaoPai == null || submenu != null) {
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(permissao.getNomeMenuVerificado());
                item.setUrl(permissao.getUrlMenuVerificado());
                itemMenuMap.put(permissao, item);
                //adicionar ao submenu quando encontrado, senao adicionar ao root
                if (submenu != null) {
                    submenu.getElements().add(item);
                } else {
                    menuModel.addElement(item);
                }
            }
        }
    }

    public void putSubmenu(Permissao permissao, Map<Permissao, DefaultSubMenu> subMenuMap, MenuModel menuModel) {
        if (permissao != null) {
            if (permissao.isPossuiMenu() && permissao.isAtivo()) {
                String url = permissao.getUrlMenuVerificado();
                if (url == null || url.trim().isEmpty()) {
                    DefaultSubMenu submenu = subMenuMap.get(permissao);
                    //caso a permissao tenha pai deve ser adicionado um submenu desse pai quando nao encontrado
                    if (submenu == null) {
                        submenu = new DefaultSubMenu();
                        submenu.setLabel(permissao.getNomeMenuVerificado());
                        subMenuMap.put(permissao, submenu);
                        Submenu pai = null;
                        Permissao permissaoPai = permissaoDAO.getInitialized(permissao.getPermissaoPai());
                        if (permissaoPai != null && permissaoPai.isAtivo()) {
                            putSubmenu(permissaoPai, subMenuMap, menuModel);
                            pai = subMenuMap.get(permissaoPai);
                        }
                        //setar submenupai
                        if (pai != null) {
                            pai.getElements().add(submenu);
                        } else {
                            //adicionar ao root apenas quando nao possuir pai
                            if (permissaoPai == null) {
                                menuModel.addElement(submenu);
                            }
                        }
                    }
                }
            }
        }
    }

}
