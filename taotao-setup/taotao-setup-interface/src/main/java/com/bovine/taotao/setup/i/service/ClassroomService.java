package com.bovine.taotao.setup.i.service;

import com.bovine.taotao.common.core.P;
import com.bovine.taotao.common.core.injecter.FrameworkService;
import com.bovine.taotao.setup.entity.Classroom;
import com.bovine.taotao.setup.model.ArticleAndClassroomModel;
import com.bovine.taotao.setup.view.ClassroomView;

/**
 * 数币课堂Service业务接口
 * @author eden
 * @date 2023年2月23日 下午7:34:03
 */
public interface ClassroomService extends FrameworkService<Classroom, Long> {

	/**
	 * 课堂列表
	 * @param bm
	 * @return
	 */
	P<ClassroomView> getClassroomList(ArticleAndClassroomModel bm);
}
