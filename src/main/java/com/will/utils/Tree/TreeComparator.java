/**
 * @Title TreeComparator.java
 * @Package com.chuangyuan.util
 * @Description
 * @Author 庄佳彬
 * @Date 2016年4月12日
 * @Version v1.0
 */
package com.will.utils.Tree;

import java.util.Comparator;


/**
 * <li>ClassName: TreeComparator</li>
 * <li>Description:</li>
 * <li>Change:</li>
 * <li>Date: 2016年4月12日 下午4:56:40</li>
 * @author 庄佳彬
 */
public class TreeComparator implements Comparator<Tree>{

	/**
	 * @see Comparator#compare(Object, Object)
	 * @param t1
	 * @param t2
	 * @return 
	 */
	@Override
	public int compare(Tree t1, Tree t2) {
		return t1.getId().hashCode() - t2.getId().hashCode();
	}

}
