package org.opennms.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opennms.netmgt.model.OnmsCategory;
import org.opennms.web.svclayer.AdminCategoryService;
import org.opennms.web.svclayer.support.DefaultAdminCategoryService.EditModel;
import org.opennms.web.svclayer.support.DefaultAdminCategoryService.NodeEditModel;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

public class CategoryController extends AbstractController {

    private AdminCategoryService m_adminCategoryService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        OnmsCategory category = null;
        
        String removeCategoryIdString = request.getParameter("removeCategoryId");
        String newCategoryName = request.getParameter("newCategoryName");
        String categoryIdString = request.getParameter("categoryid");
        String editString = request.getParameter("edit");
        String nodeIdString = request.getParameter("node");
        
        if (removeCategoryIdString != null) {
            m_adminCategoryService.removeCategory(removeCategoryIdString);
            
            return new ModelAndView(new RedirectView("/admin/categories.htm", true));
        }
        
        if (newCategoryName != null) {
            m_adminCategoryService.addNewCategory(newCategoryName);
            
            /*
             * We could be smart and take the user straight to the edit page
             * for this new category, which would be great, however it's
             * not so great if the site has a huge number of available
             * category and they need to edit category member nodes
             * from the node pages.  So, we don't do it.
             */
            return new ModelAndView(new RedirectView("/admin/categories.htm", true));
        }
        
        if (categoryIdString != null && editString != null) {
            String editAction = request.getParameter("action");
            if (editAction != null) {
                String[] toAdd = request.getParameterValues("toAdd");
                String[] toDelete = request.getParameterValues("toDelete");

                m_adminCategoryService.performEdit(categoryIdString,
                                                      editAction,
                                                      toAdd,
                                                      toDelete);

                ModelAndView modelAndView = 
                    new ModelAndView(new RedirectView("/admin/categories.htm", true));
                modelAndView.addObject("categoryid", categoryIdString);
                modelAndView.addObject("edit", null);
                return modelAndView;
            }

            EditModel model =
                m_adminCategoryService.findCategoryAndAllNodes(categoryIdString);

            return new ModelAndView("/admin/editCategory",
                                    "model",
                                    model);
        }
        
        if (categoryIdString != null) {
            return new ModelAndView("/admin/showCategory",
                                    "model",
                                    m_adminCategoryService.getCategory(categoryIdString));
        }
        
        if (nodeIdString != null && editString != null) {
            String editAction = request.getParameter("action");
            if (editAction != null) {
                String[] toAdd = request.getParameterValues("toAdd");
                String[] toDelete = request.getParameterValues("toDelete");

                m_adminCategoryService.performNodeEdit(nodeIdString,
                                                       editAction,
                                                       toAdd,
                                                       toDelete);

                ModelAndView modelAndView = 
                    new ModelAndView(new RedirectView("/admin/categories.htm", true));
                modelAndView.addObject("node", nodeIdString);
                modelAndView.addObject("edit", null);
                return modelAndView;
            }

            NodeEditModel model =
                m_adminCategoryService.findNodeCategories(nodeIdString);

            return new ModelAndView("/admin/editNodeCategories",
                                    "model",
                                    model);
        }


        List<OnmsCategory> sortedCategories
            = m_adminCategoryService.findAllCategories();
        
        return new ModelAndView("/admin/categories",
                                "categories",
                                sortedCategories);
    }

    public AdminCategoryService getAdminCategoryService() {
        return m_adminCategoryService;
    }

    public void setAdminCategoryService(
            AdminCategoryService adminCategoryService) {
        m_adminCategoryService = adminCategoryService;
    }

}
