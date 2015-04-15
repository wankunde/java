package com.wankun.annotation.test1;

public class ForumService {

	// 注解使用 @<注解名>(<成员名1>=<成员值1>,<成员名1>=<成员值1>,...)
	// 参数为数组时，使用 {值1,值2,值3,值4} 
	@NeedTest(true)
	public void deleteForum(int forumId) {
		System.out.println("删除论坛模块 : " + forumId);
	}

	@NeedTest(value = false)
	public void deleteTopic(int topicId) {
		System.out.println("删除论坛主题 : " + topicId);
	}
}
