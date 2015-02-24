package se.kth.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author AMore
 */
@ManagedBean(name="applicantManager")
@ViewScoped
public class ApplicantManager implements Serializable {
     
    private TreeNode root1;
     
    private TreeNode root2;
     
    private TreeNode selectedNode1;
     
    private TreeNode selectedNode2;
     
    @PostConstruct
    public void init() {
        root1 = new DefaultTreeNode("Root", null);
         
        TreeNode node00 = new DefaultTreeNode("Hotdog maker", root1);
        TreeNode node01 = new DefaultTreeNode("Salary", root1);
        TreeNode node10 = new DefaultTreeNode("Gambler", root1);
        TreeNode node11 = new DefaultTreeNode("Machining", root1);
         
         
         
        root2 = new DefaultTreeNode("Root2", null);
        TreeNode item0 = new DefaultTreeNode("", root2);
         
    }
 
    public TreeNode getRoot1() {
        return root1;
    }
 
    public TreeNode getRoot2() {
        return root2;
    }
 
    public TreeNode getSelectedNode1() {
        return selectedNode1;
    }
 
    public void setSelectedNode1(TreeNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }
 
    public TreeNode getSelectedNode2() {
        return selectedNode2;
    }
 
    public void setSelectedNode2(TreeNode selectedNode2) {
        this.selectedNode2 = selectedNode2;
    }
        
    public void onDragDrop(TreeDragDropEvent event) {
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
         
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
