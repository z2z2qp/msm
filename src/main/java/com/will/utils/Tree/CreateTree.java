package com.will.utils.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Will on 2016/8/19 8:57.
 */
public class CreateTree {


    /**
     * 创建树
     *
     * 最终json格式应当是
     * [{
     *     "id":"该节点编号"，
     *     "text"："该节点文字",
     *     "state":"close",//表示关闭，空表示打开
     *     "pid":"父节点id",
     *     "children":[{
     *         "id":"该节点编号"，
     *         "text"："该节点文字",
     *         "state":"close",//表示关闭，空表示打开
     *         "pid":"父节点id",
     *         "children":[]
     *     },{
     *         "id":"该节点编号"，
     *         "text"："该节点文字",
     *         "state":"close",//表示关闭，空表示打开
     *         "pid":"父节点id",
     *         "children":[]
     *     }]
     * },
     * {
     *     "id":"该节点编号"，
     *     "text"："该节点文字",
     *     "state":"close",//表示关闭，空表示打开
     *     "pid":"父节点id",
     *     "children":[{
     *         "id":"该节点编号"，
     *         "text"："该节点文字",
     *         "state":"close",//表示关闭，空表示打开
     *         "pid":"父节点id",
     *         "children":[]
     *     },{
     *         "id":"该节点编号"，
     *         "text"："该节点文字",
     *         "state":"close",//表示关闭，空表示打开
     *         "pid":"父节点id",
     *         "children":[]
     *     }]
     * }]
     *
     * @param nodeMap the node map {key = tree.id  value = tree}
     * @return the list
     * @Auth Will
     * @Date 2016 -08-19 09:02:05
     */
    public static List<Tree> create(Map<String,Tree> nodeMap){
        List<Tree> root = new ArrayList<Tree>();
        for (Tree node : nodeMap.values()) {
            if (node.getPid() != null) {
                Tree parentNode = nodeMap.get(node.getPid());
                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                    parentNode.setState("closed");
                } else
                    root.add(node);
            } else
                root.add(node);
        }
        return root;
    }
}
