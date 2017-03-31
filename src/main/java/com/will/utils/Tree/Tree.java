/**
 * @Title Tree.java
 * @Package com.chuangyuan.util
 * @Description
 * @Author 庄佳彬
 * @Date 2016年4月12日
 * @Version v1.0
 */
package com.will.utils.Tree;

import java.util.ArrayList;
import java.util.List;


/**
 * <li>ClassName: Tree</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年4月12日 下午4:46:23</li>
 * @author 庄佳彬
 */
public class Tree {

	private String id;
	private String text;
	private String state;
	private String pid;
	private List<Tree> children;
	
	
	
	
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 */
	public Tree() {
		super();
		this.state = "";
		children = new ArrayList<Tree>();
	}
	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param id
	 * @param text
	 * @param pid
	 */
	public Tree(String id, String text, String pid) {
		super();
		this.id = id;
		this.text = text;
		this.pid = pid;
		this.state = "";
		children = new ArrayList<Tree>();
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the children
	 */
	public List<Tree> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
}
