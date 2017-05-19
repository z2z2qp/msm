package com.will.utils.Tree

/**
 * Created by zoumy on 2017/5/11 8:46.
 */
object CreateTree {


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
    fun creat(nodeMap:Map<String,Tree> ):List<Tree> {
        var root: ArrayList<Tree> = ArrayList()
        nodeMap.values.forEach {
            if(it.pid != null){
                val parentNode:Tree? = nodeMap.get(it.pid!!)
                if(parentNode != null){
                    parentNode.children.add(it)
                    parentNode.state = "closed"
                }else{
                    root.add(it)
                }
            }else{
                root.add(it)
            }
        }
        return root
    }
}