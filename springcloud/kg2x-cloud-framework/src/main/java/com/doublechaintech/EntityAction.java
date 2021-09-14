package com.doublechaintech;

public enum EntityAction {
  SAVE, // 保存当前对象,以及它依赖的新建对象
  SAVE_NEW, // 遍历整个图，作结点上面自己的action, 同时总是保存新建对象
  SAVE_ALL, // 保存图中所有对象
  DELETE, // 删除当前对象，以及依赖于它的对象（cascade-delete）
  DELETE_ALL, // 删除图中所有对象
  GRAPH_VISIT // 遍历整个图，作结点上面自己的action
;

  public boolean isSave() {
    return this == SAVE || this == SAVE_ALL || this == SAVE_NEW;
  }

  public boolean isDelete() {
    return this == DELETE || this == DELETE_ALL;
  }
}
